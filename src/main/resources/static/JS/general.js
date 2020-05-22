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



createQuestion = (Questions,ID) => {

        let question_id = ID

        let currentQuestion = Questions[question_id];

        let video = `<div class="video-container">
                        <iframe width="50%" height="300px" src="https://www.youtube.com/embed/${currentQuestion.video_url}" frameborder="0" allowfullscreen></iframe>
                     </div>`;

        let question = `<br/><pre>${currentQuestion.question}</pre>`;

        let solution = `<br/><pre>${currentQuestion.solution}</pre>`

        let modal_footer = generateModalFooter(question_id,Questions.length);

        let tabs = `<div class="modal-content noScrollBar" style="height: auto" >
                        <h5 class="center" >${currentQuestion.title}</h5>
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
    return HTML.replace(/</g ,"&lt").replace(/>/g ,"&gt");
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



PreHTMLGeneration = () => {
    console.log("Generating Pre HTML")
}

generatePreviewModal = () => {

    $(document).on('change', '#question', function() {
        let question = $(this).val()
        if($('#questionFormatType').is(":checked")){
            question = generatePreHTML(question);
            question = `<code>${question}</code>`;
        }
        $('#questionFormatted').val(question)

    });

    $(document).on('click', '#questionFormatType', function() {
        let question = $('#question').val()
        if($('#questionFormatType').is(":checked")){
            question = generatePreHTML(question);
            question = `<code>${question}</code>`
        }
        $('#questionFormatted').val(question)

    });

    $(document).on('change', '#solution', function() {
        let solution = $(this).val()
        if($('#solutionFormatType').is(":checked")){
            solution = generatePreHTML(solution);
            solution = `<code>${solution}</code>`
        }
        $('#solutionFormatted').val(solution)
    });

    $(document).on('click', '#solutionFormatType', function() {
        let solution = $('#solution').val()
        if($('#solutionFormatType').is(":checked")){
            solution = generatePreHTML(solution);
            solution = `<code>${solution}</code>`
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

    let question = `<br/><pre>${$('#questionFormatted').val()}</pre>`;

    let solution = `<br/><pre>${$('#solutionFormatted').val()}</pre>`

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