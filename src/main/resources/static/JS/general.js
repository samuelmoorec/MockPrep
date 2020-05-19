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

    console.log("waiting to click")

    $(document).on("click",'.question-selector',function () {

        console.log("I was clicked")

        let question_id = parseInt($(this).attr("data-id"));



        createQuestion(Questions, question_id);


        let currentQuestion = Questions[question_id];

        selectedQuestion(currentQuestion.id);

        console.log("helloBBBBB");

    });

}



createQuestion = (Questions,ID) => {
    console.log(1)


    console.log(2)

        let question_id = ID

        let currentQuestion = Questions[question_id];

        let video = `<div class="video-container">
                        <iframe width="50%" height="300px" src="https://www.youtube.com/embed/${currentQuestion.video_url}" frameborder="0" allowfullscreen></iframe>
                     </div>`;

        let question = `<br/><p>${currentQuestion.question}</p>`;

        let solution = `<br/><p>${currentQuestion.solution}</p>`

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

        console.log(3)


        $('#question_modal').addClass("no-autoinit");


        console.log(4)


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



    let data = {}
    data["question_id"] = ID;



    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "/questionSelection",
        data: JSON.stringify(data),
        dataType: 'json',
        timeout: 600000,
        success: function (data) {
            $("#btn-update").prop("disabled", false);
            //...
        },
        error: function (e) {
            $("#btn-save").prop("disabled", false);
            //...
        }
    });
}

generatePreHTML = (HTML) => {
    return HTML.replace(/</g ,"&lt").replace(/>/g ,"&gt");
}


PreHTMLGeneration = () => {
    console.log("Generating Pre HTML")

    $(document).on('change', '#question', function() {
        console.log("event change");
        let question = $(this).val()
        console.log($('#questionFormatType').val());
        if($('#questionFormatType').is(":checked")){
            question = generatePreHTML(question);
        }
        $('#questionFormatted').val(question)

    });

    $(document).on('change', '#solution', function() {
        let solution = $(this).val()
        if($('#solutionFormatType').is(":checked")){
            solution = generatePreHTML(solution);
        }
        $('#solutionFormatted').val(solution)
    });



}

generatePreviewModal = () => {
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

    $('#questionPreview').addClass("no-autoinit");

}

