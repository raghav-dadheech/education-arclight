import { Component, OnInit, AfterViewInit, ViewChild } from '@angular/core';
import { Router } from '@angular/router';
import { FormBuilder, FormGroup, Validators } from "@angular/forms";
// import { MatOption } from '@angular/material';
import { ExamSharingService } from '../../services/exam-sharing.service';
import { QuestionService } from '../../services/question.service';
declare var $: any;
// import SlimSelect from 'slim-select'
@Component({
  selector: 'app-exam-paper',
  templateUrl: './exam-paper.component.html',
  styleUrls: ['./exam-paper.component.css']
})
export class ExamPaperComponent implements OnInit {

  visibleSection = {
    topSelection: false,
    examSelection: true,
    otherSelection: true
  }
  subjects = []
  chapters = []
  question_types = []
  no_of_questions = [
    {
      'id': 1,
      'value': 10
    },
    {
      'id': 2,
      'value': 20
    },
    {
      'id': 3,
      'value': 25
    }
  ]
  difficulty_levels = []
  exams = []
  properties: FormGroup;
  examProperties: FormGroup;
  selected_no_of_question:number
  selected_difficulty_level:number[]
  selected_question_type:number[]
  single_select_question_type:any
  selected_exam:any
  // @ViewChild('allChapters', { static: false }) private allChapters: MatOption;
  // @ViewChild('allQuestionTypes', { static: false }) private allQuestionTypes: MatOption;
  // @ViewChild('allDifficultyLevels', { static: false }) private allDifficultyLevels: MatOption;
  constructor(
    private router: Router,
    private fb: FormBuilder,
    private _examSharingService: ExamSharingService,
    private _questionService: QuestionService) {
  }

  ngOnInit() {
    this.properties = this.fb.group({
      question_type: [''],
      subject: ['', [Validators.required]],
      chapter: [''],
      no_of_question: [''],
      difficulty_level: ['']
    })
    this.examProperties = this.fb.group({
      exam: ['', [Validators.required]]
    })
    this.getProperties()
  }

  exam() { return this.examProperties.get('exam')}

  subject() {return this.properties.get('subject')}

  getProperties() {
    $('#spinner').css('display', 'flex')
    this._questionService.getProperty().subscribe((response) => {
      console.log(response)
      this.subjects = response['subjects']
      this.single_select_question_type = response['questionTypes'].splice(0, 1)[0];
      this.question_types = response['questionTypes']
      this.difficulty_levels = response['difficultyLevels']
      this.exams = response['examPapers']
      // this.selected_question_type = [this.question_types[0].id]
      this.selected_difficulty_level = [this.difficulty_levels[0].id]
      this.selected_no_of_question = this.no_of_questions[0].value
      $('#spinner').hide()
    }, (error) => {
      $('#spinner').hide()
      console.log(error);
    });
  }

  setChapters() {
    let selectedSubject = this.properties.value.subject;
    let subject = this.subjects.find((data: any) => data.id == selectedSubject);
    if (subject)
      this.chapters = subject.chapters
    else
      this.chapters = []
    var data = $.map(this.chapters, function (obj) {
      obj.text = obj.text || obj.name; // replace name with the property used for the text
      return obj;
    });
    if($('#inputChapter').data('select2'))
    {
      $('#inputChapter').select2('destroy').find('option').prop('selected', '').end().select2();
      $('#inputChapter').html('')
    }
    $('#inputChapter').select2({
      data: data,
      multiple:true
    });
  }

  takeExam(type) {
    console.log(type)
    if (type == "exam") {
      this.visibleSection = {
        topSelection: true,
        examSelection: false,
        otherSelection: true
      }
    } else {
      this.visibleSection = {
        topSelection: true,
        examSelection: true,
        otherSelection: false
      }
    }
  }

  backToTop() {
    this.visibleSection = {
      topSelection: false,
      examSelection: true,
      otherSelection: true
    }
  }

  proceed(type) {
    let examProperties = {
      'examType': type,
      'subject': null,
      'chapters': null,
      'questionTypes': null,
      "noOfQuestions": null,
      "difficultyLevels": null,
      "exam": {}
    }
    if(type == 'practice') {
      let selectedSubject = this.properties.controls.subject.value;
      // let selectedChapter = this.properties.controls.chapter.value;
      let selectedChapter = $('#inputChapter').select2('data');
      let selectedQuestionType = this.properties.controls.question_type.value;
      let selectedNoOfQuestions = this.properties.controls.no_of_question.value;
      let selectedDifficultyLevel = this.properties.controls.difficulty_level.value;
  
      let subject = this.subjects.find((data: any) => data.id == selectedSubject);
  
      let chapters = []
      if(selectedChapter != undefined) {
        for(var i = 0;i< selectedChapter.length; i++) {
          let chapter = this.chapters.find((data: any) => data.id == selectedChapter[i]['id']); 
          if(chapter != undefined)
            chapters.push(chapter)
        }
      }
  
      let question_types = []
      question_types.push(this.single_select_question_type)
      if(selectedQuestionType != undefined) {
        for(var i = 0; i < selectedQuestionType.length; i++){
          let question_type = this.question_types.find((data: any) => data.id == selectedQuestionType[i]);    
          if(question_type != undefined)
            question_types.push(question_type)
        }
      }
  
      let difficulty_levels = []
      if(selectedDifficultyLevel != undefined) {
        for(var i=0; i < selectedDifficultyLevel.length;i++) {
          let difficulty_level = this.difficulty_levels.find((data: any) => data.id == selectedDifficultyLevel[i]);    
          if(difficulty_level != undefined)
            difficulty_levels.push(difficulty_level)
        }
      }
  
      let no_of_questions = 10;
      if(selectedNoOfQuestions != undefined) {
        no_of_questions = selectedNoOfQuestions      
      }
      examProperties = {
        'examType': type,
        'subject': subject,
        'chapters': chapters,
        'questionTypes': question_types,
        "noOfQuestions": no_of_questions,
        "difficultyLevels": difficulty_levels,
        "exam": this.selected_exam
      }
    } else if(type == 'exam') {
      console.log(this.selected_exam)
      let exam = this.exams.find((data: any) => data.id == this.selected_exam);
      console.log(this.exams)
      console.log(exam)
      examProperties['exam'] = exam
    }
    
    this._examSharingService.setExamProperties(examProperties);
    // console.log(examProperties)
    this.router.navigateByUrl('/exam');
  }

  tosslePerOne(type) {
    // if (type == "chapter") {
    //   if (this.allChapters.selected) {
    //     console.log('here')
    //     this.allChapters.deselect();
    //     return false;
    //   }
    //   if (this.properties.controls.chapter.value.length == this.chapters.length)
    //     this.allChapters.select();
    // } else if (type == "question_type") {
    //   if (this.allQuestionTypes.selected) {
    //     this.allQuestionTypes.deselect();
    //     return false;
    //   }
    //   if (this.properties.controls.question_type.value.length == this.question_types.length)
    //     this.allQuestionTypes.select();
    // } else if (type == "difficulty_level") {
    //   if (this.allDifficultyLevels.selected) {
    //     this.allDifficultyLevels.deselect();
    //     return false;
    //   }
    //   if (this.properties.controls.difficulty_level.value.length == this.difficulty_levels.length)
    //     this.allDifficultyLevels.select();
    // }
  }
  toggleAllSelection(type) {
    // if (type == "chapter") {
    //   if (this.allChapters.selected) {
    //     console.log(this.chapters.map(item => item.id))
    //     this.properties.controls.chapter
    //       .patchValue([...this.chapters.map(function(item){
    //         console.log(item)
    //         return item.id
    //       }), 0]);
    //   } else {
    //     this.properties.controls.chapter.patchValue([]);
    //   }
    // } else if (type == "question_type") {
    //   if (this.allQuestionTypes.selected) {
    //     this.properties.controls.question_type
    //       .patchValue([...this.question_types.map(item => item.id), 0]);
    //   } else {
    //     this.properties.controls.question_type.patchValue([]);
    //   }
    // } else if (type == "difficulty_level") {
    //   if (this.allDifficultyLevels.selected) {
    //     this.properties.controls.difficulty_level
    //       .patchValue([...this.difficulty_levels.map(item => item.id), 0]);
    //   } else {
    //     this.properties.controls.difficulty_level.patchValue([]);
    //   }
    // }

  }

}
