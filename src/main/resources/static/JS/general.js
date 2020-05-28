let request;
let QuestionsObject;

question_AJAX_request = () => {
   request = $.ajax({'url': '/questions.json', 'async': 'false' })
    request.done((questions) => {
        QuestionsObject = questions;
        populateQuestions();

    });



}

populateQuestions = () => {

    request.done((Questions) => {
        let html = '<div class="container" style="position: relative; top: 30px">';

        // We create the variable i to let us know what iteration
        // we are on since and enhanced for loop doesnt give us one.
        let i = 0;

        Questions.forEach((question) => {

            // We check to see if the iteration is even so we know to add a div with the class of row.
            // This is what allows us to pair two cards together
            if(i % 2 === 0){    html += '<div class="row">';    }

            html += `<div class="col s12 m6 l6">
                        <div class="card light-green ">
                            <div class="card-content white-text">
                                <span class="card-title">${question.title}</span>
                                <p>${question.question}</p>
                            </div>
                            <div class="card-action light-green lighten-3">
                                <a href="#question_modal" data-id="${i}" class="modal-trigger grey-text text-darken-1 question-selector">Try It Out</a>
                                <a href="#" class="right grey-text text-darken-1 disable">${question.language}</a>
                            </div>
                        </div>
                     </div>`;

            // Here is where we decide when to put the closing tag on the div with the class of row.
            // it is added for all odd iterations or if we are on the last card
            if (i % 2 === 1 || i === Questions.size ){  html += '</div>';   }

            i++;

        });

        html += '</div>';

        $('#question_feed').html(html);

        M.AutoInit();

        QuestionProcedure(Questions);




    })





}

QuestionProcedure = (Questions) => {


    $(document).on("click",'.question-selector',function () {

        let question_id = parseInt($(this).attr("data-id"));

        createQuestion(Questions, question_id);

        let currentQuestion = Questions[question_id];

        selectedQuestion(currentQuestion.id);


    });

}


formatTimeStamp = (TimeStamp) => {
    console.log("getting Day");
    let day = TimestampDay(TimeStamp);
    console.log("getting Month");
    let month = TimestampMonth(TimeStamp);
    console.log("getting Date");

    let date = TimestampDate(TimeStamp);
    console.log("getting Year");

    let year = TimestampYear(TimeStamp);
    console.log("getting Time");

    let time = TimestampTime(TimeStamp);

    return `${time} ${day}, ${month} ${date}, ${year}`

}

TimestampDay = (TimeStamp) => {
    let timeStamp = new Date(TimeStamp);
    switch (timeStamp.getDay()) {
        case 0:
            return "Sunday";
        case 1:
             return "Monday";
        case 2:
            return "Tuesday";
        case 3:
            return "Wednesday";
        case 4:
            return "Thursday";
        case 5:
            return "Friday";
        case 6:
            return "Saturday";
    }
}

TimestampMonth = (TimeStamp) => {
    let timeStamp = new Date(TimeStamp);
    switch (timeStamp.getMonth()) {
        case 0:
            return "January";
        case 1:
            return "February";
        case 2:
            return "March";
        case 3:
            return "April";
        case 4:
            return "May";
        case 5:
            return "June";
        case 6:
            return "July";
        case 7:
            return "August";
        case 8:
            return "September";
        case 9:
            return "October";
        case 10:
            return "November";
        case 11:
            return "December";
    }
}

TimestampDate = (TimeStamp) => {
    let timeStamp = new Date(TimeStamp);
    return timeStamp.getDate();
}

TimestampYear = (TimeStamp) => {
    let timeStamp = new Date(TimeStamp);
    return timeStamp.getFullYear();
}

TimestampTime = (TimeStamp) => {
    let timeStamp = new Date(TimeStamp);
    let min = timeStamp.getMinutes().toString();
    if (min.length === 1){
        min = "0" + min;
    }
    switch (timeStamp.getHours()) {
        case 0:
            return `12:${min} AM`;
        case 1:
            return `01:${min} AM`;
        case 2:
            return `02:${min} AM`;
        case 3:
            return `03:${min} AM`;
        case 4:
            return `04:${min} AM`;
        case 5:
            return `05:${min} AM`;
        case 6:
            return `06:${min} AM`;
        case 7:
            return `07:${min} AM`;
        case 8:
            return `08:${min} AM`;
        case 9:
            return `09:${min} AM`;
        case 10:
            return `10:${min} AM`;
        case 11:
            return `11:${min} AM`;
        case 12:
            return `12:${min} AM`;
        case 13:
            return `01:${min} PM`;
        case 14:
            return `02:${min} PM`;
        case 15:
            return `03:${min} PM`;
        case 16:
            return `04:${min} PM`;
        case 17:
            return `05:${min} PM`;
        case 18:
            return `06:${min} PM`;
        case 19:
            return `07:${min} PM`;
        case 20:
            return `08:${min} PM`;
        case 21:
            return `09:${min} PM`;
        case 22:
            return `10:${min} PM`;
        case 23:
            return `11:${min} PM`;
    }
}



createQuestion = (Questions,ID) => {

        let question_id = ID

        let currentQuestion = Questions[question_id];

        let video = `<div class="video-container">
                        <iframe width="50%" height="300px" src="https://www.youtube.com/embed/${currentQuestion.video_url}" frameborder="0" allowfullscreen></iframe>
                     </div>`;

        let question = `<br/>${currentQuestion.question}`;

        let solution = `<br/>${currentQuestion.solution}`

        let modal_footer = generateModalFooter(question_id,Questions.length);

        let tabs = `<div class="modal-content noScrollBar" style="height: auto" >
                        <h5 class="center" >${currentQuestion.title}</h5>
                        <div class="row">
                            <div class="col s12 ">
                                <ul class="tabs"  >
                                  <li class="tab col s4"><a href="#test1" class="active">Question</a></li>
                                  <li class="tab col s4"><a href="#test2" >Answer</a></li>
                                  <li class="tab col s4"><a href="#test4" >Video</a></li>
                                </ul>
                            </div>
                            <div id="test1" class="col s12">${question}</div>
                            <div id="test2" class="col s12">${solution}</div>
                            <div id="test4" class="col s12">${video}</div>
                        </div>
                        ${modal_footer}
                    </div>`;

        $('#question_modal').html(tabs);

        $('#question_modal').addClass("no-autoinit");

    M.AutoInit();


}

generateModalFooter = (questionID,QuestionObjectSize) => {

    let footerHTML = `<div class="modal-footer">
                          <a href="#question_modal" data-id="${questionID - 1}" class="modal-trigger waves-effect waves-green btn-flat left question-selector">Previous</a>
                          <a href="#question_modal" data-id="${questionID + 1}" class="modal-trigger waves-effect waves-green btn-flat right question-selector">Next</a>
                      </div>`;

    if (questionID === 0) {

        footerHTML = `<div class="modal-footer">
                          <a href="#question_modal"  data-id="${questionID + 1}" class="modal-trigger waves-effect waves-green btn-flat right question-selector">Next</a>
                      </div>`;

    } else if (questionID === QuestionObjectSize - 1) {

        footerHTML = `<div class="modal-footer"><a href="#question_modal" data-id="${questionID - 1}" class="modal-trigger waves-effect waves-green btn-flat left question-selector">Previous</a></div>`;
    }

    return footerHTML;
}

selectedQuestion = (ID) => {

    let token = $("meta[name='_csrf']").attr("content");
    let header = $("meta[name='_csrf_header']").attr("content");


    let data = {}
    data["question_id"] = ID;
    $(document).ajaxSend(function(e, xhr, options) {
        xhr.setRequestHeader(header, token);
    });


    $.post({
        contentType: "application/json",
        url: "/questionSelection",
        data: JSON.stringify(data),
        timeout: 600000,
        success: function (data) {
            $("#btn-update").prop("disabled", false);
        },
        error: function (e) {
            $("#btn-save").prop("disabled", false);
            console.log(e)
            //...
        }
    });
}

//"SyntaxError: Failed to execute 'setRequestHeader' on 'XMLHttpRequest': '${_csrf.headerName}' is not a valid HTTP header field name."

generatePreHTML = (HTML) => {
    let preHTML = `<pre><code>`;

    preHTML += HTML.replace(/</g ,"&lt").replace(/>/g ,"&gt");

    return preHTML + `</pre></code>`
}

generateHTML = (PreHTML) => {
    if (PreHTML.includes("<code>")) {
        return  PreHTML.replace("<code>", "")
            .replace("</code>", "")
            .replace("<pre>", "")
            .replace("</pre>", "")
            .replace(/&lt/g, "<")
            .replace(/&gt/g, ">")
            .trim();

    }else {
        return PreHTML;
    }
}

adminActivityAjax = () => {
    let request = $.ajax({'url': 'activitiesDescByTimeStamp.json'});
    request.done(function (activities) {

        let html = `<table>
                            <tr>
                                <th>First</th>
                                <th>Last</th>
                                <th>Username</th>
                                <th>Activity</th>
                                <th>TimeStamp</th>
                            </tr>`;

        activities.forEach(function(activity) {

            html += `<tr>
                            <td>${activity.user.first_name}</td>
                            <td>${activity.user.last_name}</td>
                            <td>${activity.user.username}</td>
                            <td>${activity.type}</td>
                            <td>${formatTimeStamp(activity.timestamp)}</td>
                         </tr>`;
        });

        html += '</table>'

        $('#activityFeed').html(html);
    });

}

adminQuestionsAjax = () => {
    let request = $.ajax({'url': 'questions.json'});
    request.done(function (questions) {

        let html = `<table>
                            <tr>
                                <th>ID</th>
                                <th>Title</th>
                                <th>Level</th>
                                <th>Language</th>
                                <th>Edit</th>
                                <th>Delete</th>
                            </tr>`;

        questions.forEach(function(question) {

            html += `<tr>
                            <td>${question.id}</td>
                            <td>${question.title}</td>
                            <td>${question.level}</td>
                            <td>${question.language}</td>
                            <td><a class="edit_button" href="/editQuestion?question=${question.id}">Edit</a></td>
                            <td><a class="remove_button" href="/deleteQuestion/${question.id}">Remove</a></td>
                         </tr>`;
        });

        html += '</table>'

        $('#questionsList').html(html);
    });
}



PreHTMLGeneration = () => {
    console.log("Generating Pre HTML")
}

generatePreviewModal = () => {

    $(document).on('change', '#question', function() {
        let question = $(this).val()
        if($('#questionFormatType').is(":checked")){
            question = generatePreHTML(question);
            question = `${question}`;
        }
        $('#questionFormatted').val(question)

    });

    $(document).on('click', '#questionFormatType', function() {
        let question = $('#question').val()
        if($('#questionFormatType').is(":checked")){
            question = generatePreHTML(question);
            question = `${question}`
        }
        $('#questionFormatted').val(question)

    });

    $(document).on('change', '#solution', function() {
        let solution = $(this).val()
        if($('#solutionFormatType').is(":checked")){
            solution = generatePreHTML(solution);
            solution = `${solution}`
        }
        $('#solutionFormatted').val(solution)
    });

    $(document).on('click', '#solutionFormatType', function() {
        let solution = $('#solution').val()
        if($('#solutionFormatType').is(":checked")){
            solution = generatePreHTML(solution);
            solution = `${solution}`
        }
        $('#solutionFormatted').val(solution)

    });

    PreviewModal();

    console.log("Generating Preview Modal")
    $(document).on('click','#previewmodal', function () {
        PreviewModal();
    });

}

PreviewModal = () => {

    let video = `<div class="video-container">
                        <iframe width="50%" height="300px" src="https://www.youtube.com/embed/${$('#solution_video').val()}" frameborder="0" allowfullscreen></iframe>
                     </div>`;

    let question = `<br/>${$('#questionFormatted').val()}`;

    let solution = `<br/>${$('#solutionFormatted').val()}`

    let tabs = `<div class="modal-content noScrollBar" style="height: auto" >
                        <h5 class="center" >${$('#subject').val()}</h5>
                        <div class="row">
                            <div class="col s12 ">
                                <ul class="tabs"  >
                                  <li class="tab col s4"><a href="#test1" class="active light-green-text text-darken-1">Question</a></li>
                                  <li class="tab col s4"><a href="#test2" class="light-green-text text-darken-1">Answer</a></li>
                                  <li class="tab col s4"><a href="#test4" class="light-green-text text-darken-1">Video</a></li>
                                </ul>
                            </div>
                            <div id="test1" class="col s12">${question}</div>
                            <div id="test2" class="col s12">${solution}</div>
                            <div id="test4" class="col s12">${video}</div>
                        </div>
                    </div>`;

    $('#questionPreview').html(tabs);

    M.AutoInit();

    $('#questionPreview').addClass("no-autoinit");
}

getQuestionID = () => {

    let queryString = window.location.search;
    const urlParam = new URLSearchParams(queryString);
    return urlParam.get('question');
}

getQuestionAJAX = () => {
    let QuestionID = getQuestionID();
    let questionJSONURL = `/question/${QuestionID}.json`;

    (function($) {
        let request = $.ajax({url : questionJSONURL});
        request.done(function (questions) {
            let currentQuestion = questions[0];
            $('#question_id').val(currentQuestion.id);
            $('#subject').val(currentQuestion.title);
            $('#language').val(currentQuestion.language);
            $('#level').val(currentQuestion.level);
            $('#question').val(generateHTML(currentQuestion.question));
            $('#questionFormatted').val(currentQuestion.question);
            if ($('#questionFormatted').val().includes("<code>")){
                $('#questionFormatType').prop('checked',true);
            }
            $('#solution').val(generateHTML(currentQuestion.solution));
            $('#solutionFormatted').val(currentQuestion.solution);
            if ($('#solutionFormatted').val().includes("<code>")){
                $('#solutionFormatType').prop('checked',true);
            }
            $('#resource').val(currentQuestion.resource);
            $('#solution_video').val(currentQuestion.video_url);
            console.log(questions);
        });
    })(jQuery);
}