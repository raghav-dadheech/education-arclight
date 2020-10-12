import { Component, OnInit, OnDestroy } from '@angular/core';
import { QuestionService } from '../../services/question.service';
import { Router} from '@angular/router';
// import {MatSnackBar,MatSnackBarConfig} from '@angular/material/snack-bar';
import { ExamSharingService } from '../../services/exam-sharing.service';
import { ToastrService } from 'ngx-toastr';
declare var $ : any;

@Component({
  selector: 'app-exam',
  templateUrl: './exam.component.html',
  styleUrls: ['./exam.component.css']
})
export class ExamComponent implements OnInit, OnDestroy {

  loaded = false;
  questions:any
  questionImages = {}
  hideExam = false;
  currentSlideIndex = 1;
  totalTime: number = 30*60;
  warnTime:number = 10*60;
  dangerTime:number = 5*60;
  timeLeft: number = this.totalTime;
  // showTimeLeft: Date;
  interval;
  response = {
    noOfQuestions:0,
    totalMarks:0,
    questionsAttempted:0,
    questionsNotAttempted:0,
    correctAnswers:0,
    marksObtained:0,
    correctMarks :0,
    negativeMarks:0,
    questions: []
  }
  correctMarks = 4
  incorrectMark = -1

  questionsStatus = {

  }
  finalResponses = []

  buttons = {
    saveAndNextDisabled:true,
    saveAndMarkForReviewDisabled:true,
    clearResponseDisabled:true,
    notVisited:0,
    notAnswered:0,
    answered:0,
    markedForReview:0,
    answeredAndMarkedForReview:0,
  }
  
  constructor(
    private _questionService: QuestionService,
    private router: Router,
    // private _snackBar: MatSnackBar,
    private _examSharingService: ExamSharingService,
    private toastr: ToastrService) { }

  ngOnInit() {
    let examProperties = this._examSharingService.getExamProperties()
    console.log(examProperties)
    if( examProperties == undefined ) {
      this.openSnackBar('warn', '', "Inappropriate selection!!!");
      this.router.navigateByUrl('/exam-paper');
    } else {
      this.loadExam(examProperties)
    }
  }

  loadExam(examProperties) {
    this.loaded = false;
    var data = {}
    if(examProperties.examType == 'practice') {
      data = {
        "pageNo": 1,
        "pageSize": examProperties.noOfQuestions,
        "subject": examProperties.subject,
        "chapters": examProperties.chapters,
        "questionTypes": examProperties.questionTypes,
        "difficultyLevels": examProperties.difficultyLevels,
        "examType": "PRACTICE"
      }
      $('#spinner').css('display', 'flex')
      this._questionService.listExamPracticeQuestions(data).subscribe((response) => {
        console.log(response);
        this.questions = response['questions']
        this.questionImages = response['questionImages']
        this.addQuestions(response);
        $('#spinner').hide()
      }, (error) => {
        console.log(error);
        $('#spinner').hide()
      });
    } if(examProperties.examType == 'exam') {
      data = {
        "examPaper": examProperties.exam,
        "questionTypes": [],
        "difficultyLevels": [],
        "examType": "PAPER"
      }
      $('#spinner').css('display', 'flex')
      this._questionService.listExamPaperQuestions(data).subscribe((response) => {
        console.log(response);
        this.questions = response['questions']
        this.questionImages = response['questionImages']
        this.addQuestions(response);
        $('#spinner').hide()
      }, (error) => {
        console.log(error);
        $('#spinner').hide()
      });
    }
  }

  addQuestions(response) {
    var questions = response['questions']
    this.questionImages = response['questionImages']
    var target = $('.carousel-inner');
    target.html('');
    for(var i = 0 ;i< questions.length;i++) {
      var carouselItem = $('<div></div>');
      if( i == 0 )
        carouselItem.attr('class','carousel-item active');
      else
        carouselItem.attr('class','carousel-item');

      var card = $('<div></div>');
      card.attr('class', 'card');
      card.attr('data-node-type', 'question-container');
      card.attr('data-question-type', questions[i]['questionType']['name']);
      card.attr('data-question-id', questions[i]['id']);
      card.attr('data-question-answer', questions[i]['answer']);
      card.attr('data-slide-no', (i+1));

      var cardHeader = $('<div></div>');
      cardHeader.attr('class', 'card-header');
      cardHeader.html("Question - "+(i+1));
      
      var cardBody = $('<div></div>');
      cardBody.attr('class', 'card-body question-container-body');
      // console.log(questions[i]['question'])
      cardBody.html(this.getImage(questions[i]));
      cardBody.on('click', function() {
        examComponent.questionBodyClicked(this)
      })
      var cardFooter1 = null
      if(questions[i]['questionType']['name'] == "Number") {
        cardFooter1 = $('<div></div>');
        cardFooter1.attr('class', 'card-footer question-container-footer p-0 col-3 ml-2');
        cardFooter1.css("position","absolute")
        cardFooter1.css("bottom","45px")
        var t = $('<input>')
        t.attr('type', 'text')
        t.attr('class', 'form-control')
        t.on('keyup', function() {
          examComponent.questionBodyClicked(cardBody)
        })
        cardFooter1.append(t);
      }
      var cardFooter = $('<div></div>');
      cardFooter.attr('class', 'card-footer question-container-footer');

      var prevAnchor = $('<a></a>');
      // prevAnchor.attr('id', 'onboard-prev');
      prevAnchor.attr('href', '#carouselExampleControls');
      prevAnchor.attr('role', 'button');
      prevAnchor.attr('data-slide', 'prev');
      prevAnchor.attr('class', 'float-left mb-2');
      prevAnchor.html('Back');

      var nextAnchor = $('<a></a>');
      // prevAnchor.attr('id', 'onboard-prev');
      nextAnchor.attr('href', '#carouselExampleControls');
      nextAnchor.attr('role', 'button');
      nextAnchor.attr('data-slide', 'next');
      nextAnchor.attr('class', 'float-right mb-2');
      nextAnchor.html('Next')
      var submit = this.submit
      var examComponent = this

      if(i == 0) {
        cardFooter.css('justify-content','end')
        // cardFooter.append(prevAnchor);
        cardFooter.append(nextAnchor);
      } else if(i == questions.length-1) {
        cardFooter.append(prevAnchor);
        // cardFooter.append(nextAnchor);
      } else {
        cardFooter.append(prevAnchor);
        cardFooter.append(nextAnchor);
      }

      card.append(cardHeader);
      card.append(cardBody);
      if(cardFooter1)
        card.append(cardFooter1);
      card.append(cardFooter);

      carouselItem.append(card);
      target.append(carouselItem);
    }

    var currentSlideIndex = this.currentSlideIndex
    $('#carouselExampleControls').on('slid.bs.carousel', function () {
      var index = $(this).find('.active').index() + 1;
      examComponent.gotToSlideToNumber(index);
    })
    $('#carouselExampleControls').on('slide.bs.carousel', function () {
      var index = $(this).find('.active').index() + 1;
      // examComponent.updateAnswer(index, examComponent)
    })
    
    var pagination = $('#pagination')
    pagination.html('')
    for(var i = 0;i<questions.length;i++) {
      var index = i+1;
      var li = $('<li></li>')
      li.attr('class', 'page-item')
      var a = $('<a></a>')
      a.attr('data-color-node',index)
      a.on('click', function() { examComponent.gotToSlide(this)})
      a.html(index)
      li.append(a)
      pagination.append(li)
    }
    this.prepareQUestionStatus();

    this.loaded = true;
    this.startTimer()
  }

  getImage(question) {
    if(this.questionImages[question['id']]) {
      var questionStr = question['question']
      var temp = this.questionImages[question['id']];
      jQuery.each(temp, function(key, val) {
        var imageStr = 'src="data:image/'+val['imageFormat']+';base64,'+val['questionImage']+'"'
        questionStr = questionStr.replace(key, imageStr)
      });
      question['question'] = questionStr;
      return questionStr;
    } else {
      return question['question'];
    }
  }

  prepareQUestionStatus() {
    for(var i = 0 ;i< this.questions.length;i++) {
      var question = this.questions[i];
      var questionNumber = i +1
      this.questionsStatus[questionNumber] = {
        "id": question["id"],
        "status": i == 0 ? "notAnswered": "notVisited",
        "answer": question['answer'],
        "correctAnswer":question["answer"],
        "questionType": question["questionType"]["name"]
      }
      // this.finalResponses[question.id] = {
      //   'selectedAnswer': null,
      //   'correctAnswer': question["answer"],
      //   'questionType': question["questionType"]["name"],
      //   'status': i == 0 ? "notAnswered": "notVisited",
      // }
    }
    this.buttons.notAnswered++;
    this.buttons.notVisited = this.questions.length-1
    this.updateQuestionColors(this)
  }

  updateQuestionColors(examComponent) {
    console.log(this.questionsStatus)
    for(var questionNumber in this.questionsStatus) {
      var object = this.questionsStatus[questionNumber]
      var clazz = "";
      if(object.status == "notVisited")
        clazz = "que-not-visited test-ques-stats";
      else if(object.status == "notAnswered")
        clazz = "que-not-answered test-ques-stats";
      else if(object.status == "answered")
        clazz = "test-ques-stats que-saved";
      else if(object.status == "answeredAndMarkedForReview")
        clazz = "test-ques-stats que-saved-marked-for-review";
      else if(object.status == "markedForReview")
        clazz = "test-ques-stats que-marked-for-review";
      $('a[data-color-node="'+questionNumber+'"]').attr('class', clazz);
    }
  }

  submit(timeOut) {
    var index = $('#carouselExampleControls').find('.active').index() + 1;
    var flag = false;
    var questionType = $('#carouselExampleControls').find('.active div.card').attr('data-question-type');
    if(questionType == "Number")
      flag = $('#carouselExampleControls').find('.active div.card input[type="text"]').val().trim().length > 0;
    else
      flag = $('#carouselExampleControls').find('.active').find('input:checked').length > 0;
    if(flag && this.questionsStatus[index].status == "notAnswered" && !timeOut) {
      console.log('alert')
      $('#submitConfirmationModal').modal('show')
      $("#submitConfirmationModal").appendTo("body");
    } else {
      this.finalSubmit()
    }  
  }

  ngOnDestroy(){
    $("body>#submitConfirmationModal").remove();
  }

  finalSubmit() {
    clearInterval(this.interval);
    $('#submitConfirmationModal').modal('hide')
    $(".modal-backdrop").remove();
    this.updateAnswer(this)
    console.log(this.finalResponses)
    this.showExamResponse(this.finalResponses, this); 
  }

  showExamResponse(responses, examComponent) {
    var object = {
      noOfQuestions:0,
      totalMarks:0,
      questionsAttempted:0,
      questionsNotAttempted:0,
      correctAnswers:0,
      marksObtained:0,
      correctMarks :0,
      negativeMarks:0,
      questions: []
    };
    // var questions = examComponent.questions
    var tempArray = []
    object.noOfQuestions = responses.length
    object.totalMarks = responses.length
    // object.questionsAttempted = responses.length
    object.marksObtained = 0
    object.correctAnswers = 0;
    $.each(responses, function(index, question){
      console.log(question)
      var correctAnswer = null;
      if(question.questionType == "Single Select" || question.questionType == "Number")
        correctAnswer = question.correctAnswer
      else if(question.questionType == "Multiple Select")
        correctAnswer = question.correctAnswer ? question.correctAnswer.join(",") : null;
      if(question.status == "answered" || question.status == "answeredAndMarkedForReview") {
        //question attempted
        var selectedAnswer = question.selectedAnswer
        object.questionsAttempted++
        if(selectedAnswer == correctAnswer) {
          object.correctAnswers++;
          // object.marksObtained++;
          tempArray.push({'question': question.question, 'correctAnswer': correctAnswer, 'selectedAnswer': selectedAnswer, 'correct': true, 'class': 'fa fa-check-circle text-success', 'solution':question.solution})
        } else {
          tempArray.push({'question': question.question, 'correctAnswer': correctAnswer, 'selectedAnswer': selectedAnswer, 'correct': false, 'class': 'fa fa-times-circle text-danger', 'solution':question.solution})
        }
      } else {
        //question not attempted
        tempArray.push({'question': question.question, 'correctAnswer': correctAnswer, 'selectedAnswer': 'N/A', 'correct': false, 'class': 'fa fa-times-circle text-danger', 'solution':question.solution})
      }
      var correctAnswer = question.answer;
    })
    object.questionsNotAttempted = object.noOfQuestions - object.questionsAttempted
    object.correctMarks = object.correctAnswers * this.correctMarks
    object.negativeMarks = (object.questionsAttempted - object.correctAnswers) * this.incorrectMark
    object.marksObtained = object.correctMarks + object.negativeMarks
    object.questions = tempArray
    examComponent.response = object;
    console.log(tempArray)
    examComponent.populateResponseOnUI(tempArray)
  }

  populateResponseOnUI(questions) {
    var target = $('#response-container')
    target.html('')
    for(var i =0;i< questions.length; i++) {
      var question = questions[i];

      var container = $('<div></div>')
      var row = $('<div></div>')
      row.attr('class', 'row text-black-50 .bg-light')

      var div1 = $('<div></div>');
      div1.attr('class', 'col-12')
      div1.html('<b>Question ('+(i+1)+').</b>')

      var hr1 = $('<hr>');

      var div2 = $('<div></div>');
      div2.attr('class', 'col-12')
      div2.html(question.question)

      var hr2 = $('<hr>');

      var div3 = $('<div></div>');
      div3.attr('class', 'col-6');

      var div4 = $('<div></div>');
      div4.attr('class', 'col-6 float-left p-0');
      div4.html('Correct answer:');

      var div5 = $('<div></div>');
      div5.attr('class', 'col-6 float-left p-0');
      div5.html(question.correctAnswer);

      div3.append(div4);
      div3.append(div5);

      var div6 = $('<div></div>');
      div6.attr('class', 'col-6');

      var div7 = $('<div></div>');
      div7.attr('class', 'col-6 float-left p-0');
      div7.html('Your answer:');

      var div8 = $('<div></div>');
      div8.attr('class', 'col-6 float-left p-0');
      div8.html(question.selectedAnswer+' <i class="'+question.class+'">');

      div6.append(div7);
      div6.append(div8);

      var hr3 = $('<hr>');

      row.append(div1);
      row.append(hr1);
      row.append(div2);
      row.append(hr2);
      row.append(div3);
      row.append(div6);

      // console.log('correctAnswer  ', question.correctAnswer)
      // console.log(row.find('input[value="'+question.correctAnswer+'"]'))
      row.find('input[value="'+question.selectedAnswer+'"]').attr('checked','true')
      container.append(row)
      container.append(hr3);

      target.append(container);
      if(question.solution != null) {
        var solution = $('<div></div>')
        solution.attr('style', 'height: 350px;width: 100%;overflow: auto;')
        var solImg = $('<img></img>')
        solImg.attr('src', question.solution);
        solution.append(solImg);
        target.append(solution);
      }
      target.find('input').attr('disabled','disabled')
    }
    this.hideExam = !this.hideExam;
  }
  backToExam() {
    this.hideExam = !this.hideExam;
    this.timeLeft = this.totalTime
    clearInterval(this.interval);
    // this.loadExam();
    this.router.navigateByUrl('/exam-paper');
  }
  startTimer() {
    this.timeLeft = this.totalTime
    $('#time-button').removeClass('btn-danger')
    $('#time-button').addClass('btn-primary')
    this.interval = setInterval(() => {
      // console.log(this.timeLeft)
      if(this.timeLeft > 0) {
        this.timeLeft--;
        if(this.timeLeft < this.dangerTime) {
          //danger
          $('#time-button').removeClass('btn-warning')
          $('#time-button').addClass('btn-danger')
        } else if(this.timeLeft < this.warnTime) {
          //warn
          $('#time-button').removeClass('btn-primary')
          $('#time-button').addClass('btn-warning')
        }
      } else {
        this.timeLeft = this.totalTime;
        this.timeOver()
      }
    },1000)
  }

  timeOver() {
    clearInterval(this.interval);
    $('#submitConfirmationModal').modal('hide')
    $(".modal-backdrop").remove();
    this.openSnackBar('info', 'Time out!!!', '');
    this.submit(true);
  }

  gotToSlide(node) {
    var slideNumber = $(node).attr('data-color-node')
    this.gotToSlideToNumber(slideNumber)
  }

  gotToSlideToNumber(slideNumber) {
    console.log(slideNumber, '  --  ', this.questionsStatus[slideNumber])
    if(this.questionsStatus[slideNumber] && this.questionsStatus[slideNumber]['status'] == "notVisited") {
      this.questionsStatus[slideNumber]['status'] = 'notAnswered'
      this.buttons.notVisited--;
      this.buttons.notAnswered++;
      this.buttons.saveAndNextDisabled = true;
      this.buttons.saveAndMarkForReviewDisabled = true;
      this.buttons.clearResponseDisabled = true
    } else if(this.questionsStatus[slideNumber] && $('div[data-slide-no="'+slideNumber+'"]').find('input:checked').length == 0) {
      this.buttons.saveAndNextDisabled = true;
      this.buttons.saveAndMarkForReviewDisabled = true;
      this.buttons.clearResponseDisabled = true
    } else if($('div[data-slide-no="'+slideNumber+'"]').find('input:checked').length > 0) {
      this.buttons.saveAndNextDisabled = false;
      this.buttons.saveAndMarkForReviewDisabled = false;
      this.buttons.clearResponseDisabled = false
    }
    $('#carouselExampleControls').carousel(slideNumber-1);
    this.updateQuestionColors(this)
  }

  questionBodyClicked(questionBody) {
    var card = $(questionBody).closest('.card');
    var questionType = card.attr('data-question-type')
    var enableButtons = false;
    if(questionType == "Number")
      enableButtons = $('.carousel-item.active div.card input[type="text"]').val().trim().length > 0
    else
      enableButtons = $(questionBody).find('input:checked').length > 0
    if(enableButtons) {
      this.buttons.saveAndNextDisabled = false;
      this.buttons.saveAndMarkForReviewDisabled = false;
      this.buttons.clearResponseDisabled = false
    } else {
      this.buttons.saveAndNextDisabled = true;
      this.buttons.saveAndMarkForReviewDisabled = true;
      this.buttons.clearResponseDisabled = true
    }
    // this.updateAnswer(this);
  }

  saveAndNext() {
    console.log('saveAndNext')
    var index = $('#carouselExampleControls').find('.active').index() + 1;
    if(this.questionsStatus[index]['status'] == "notAnswered") {
      this.questionsStatus[index]['status'] = "answered";
      this.buttons.notAnswered--;
      this.buttons.answered++;
    } else if(this.questionsStatus[index]['status'] == "markedForReview") {
      this.questionsStatus[index]['status'] = "answered";
      this.buttons.markedForReview--;
      this.buttons.answered++;
    } else if(this.questionsStatus[index]['status'] == "answeredAndMarkedForReview") {
      this.questionsStatus[index]['status'] = "answered";
      this.buttons.answeredAndMarkedForReview--;
      this.buttons.answered++;
    }
    this.gotToSlideToNumber(index+1)
  }

  saveAndMarkForReview() {
    console.log('saveAndMarkForReview')
    var index = $('#carouselExampleControls').find('.active').index() + 1;
    if(this.questionsStatus[index]['status'] == "notAnswered") {
      this.questionsStatus[index]['status'] = "answeredAndMarkedForReview";
      this.buttons.notAnswered--;
      this.buttons.answeredAndMarkedForReview++;
    } else if(this.questionsStatus[index]['status'] == "markedForReview") {
      this.questionsStatus[index]['status'] = "answeredAndMarkedForReview";
      this.buttons.markedForReview--;
      this.buttons.answeredAndMarkedForReview++;
    } else if(this.questionsStatus[index]['status'] == "answered") {
      this.questionsStatus[index]['status'] = "answeredAndMarkedForReview";
      this.buttons.answered--;
      this.buttons.answeredAndMarkedForReview++;
    }
    this.gotToSlideToNumber(index+1)
  }

  clearResponse() { 
    $('#carouselExampleControls').find('.active').find('input:checked').each(function(index, node){
      console.log(node)
      node.checked = false
    })
    console.log('clearResponse')
    var index = $('#carouselExampleControls').find('.active').index() + 1;
    if(this.questionsStatus[index]['status'] == "markedForReview") {
      this.questionsStatus[index]['status'] = "notAnswered";
      this.buttons.markedForReview--;
      this.buttons.notAnswered++;
    } else if(this.questionsStatus[index]['status'] == "answered") {
      this.questionsStatus[index]['status'] = "notAnswered";
      this.buttons.answered--;
      this.buttons.notAnswered++;
    } else if(this.questionsStatus[index]['status'] == "answeredAndMarkedForReview") {
      this.questionsStatus[index]['status'] = "notAnswered";
      this.buttons.answeredAndMarkedForReview--;
      this.buttons.notAnswered++;
    }
    this.buttons.saveAndNextDisabled = true;
    this.buttons.saveAndMarkForReviewDisabled = true;
    this.buttons.clearResponseDisabled = true
    this.updateQuestionColors(this)
  }

  markForReviewAndNext() {
    console.log('markForReviewAndNext')
    var index = $('#carouselExampleControls').find('.active').index() + 1;
    if(this.questionsStatus[index]['status'] == "notAnswered") {
      this.questionsStatus[index]['status'] = "markedForReview";
      this.buttons.notAnswered--;
      this.buttons.markedForReview++;
    } else if(this.questionsStatus[index]['status'] == "answeredAndMarkedForReview") {
      this.questionsStatus[index]['status'] = "markedForReview";
      this.buttons.answeredAndMarkedForReview--;
      this.buttons.markedForReview++;
    } else if(this.questionsStatus[index]['status'] == "answered") {
      this.questionsStatus[index]['status'] = "markedForReview";
      this.buttons.answered--;
      this.buttons.markedForReview++;
    }
    this.gotToSlideToNumber(index+1)
  }

  updateAnswer(examComponent) {
    $('.question-container-body').each(function(index, questionBody){
      var card = $(questionBody).closest('.card');
    var questionNumber = card.attr('data-slide-no');
    var question = examComponent.questionsStatus[questionNumber];
    // if($(questionBody).find('input:checked').length > 0)
    var temp = examComponent.questions.filter(function(t){ return question.id == t.id})[0]
    console.log(question)
    if(question.questionType == "Single Select") {
      var selectedValue = $(card).find('.card-body input:checked'). val();
      // if(selectedValue == undefined) return;
        examComponent.finalResponses.push({
        'id':question.id,
        'selectedAnswer': selectedValue,
        'correctAnswer': question.correctAnswer,
        'questionType': question.questionType,
        'status': question.status,
        'question': temp.question,
        'solution': temp.solution,
      })
    } else if(question.questionType == "Multiple Select") {
      var answerArray = []
      // console.log($(card).find('.card-body input:checked')),
      $(card).find('.card-body input:checked').each(function(idx, selectedCheckbox){
        var selectedValue = $(selectedCheckbox).val();
        answerArray.push(selectedValue)
      });
      // if(answerArray.length == 0) return;
      examComponent.finalResponses.push({
        'id':question.id,
        'selectedAnswer': answerArray.length == 0 ? undefined : answerArray.join(","),
        'correctAnswer': question.correctAnswer.split(','),
        'questionType': question.questionType,
        'status': question.status,
        'question': temp.question
      })
    } else if(question.questionType == "Number") {
      var selectedValue = $(card).find('input[type="text"]'). val().trim();
      // if(selectedValue == undefined || selectedValue.length == 0) return;
      examComponent.finalResponses.push({
        'id':question.id,
        'selectedAnswer': selectedValue,
        'correctAnswer': question.correctAnswer,
        'questionType': question.questionType,
        'status': question.status,
        'question': temp.question
      })
    }
    })
    // var responses = this.finalResponses
    
    // return responses;
  }

  openSnackBar(type, title, message) {
    if(type == "success")
      this.toastr.success(message, title);
    else if(type == "error")
      this.toastr.error(message, title);
    else if(type == "warn")
      this.toastr.warning(message, title);
    else
      this.toastr.info(message, title);
  }
}
