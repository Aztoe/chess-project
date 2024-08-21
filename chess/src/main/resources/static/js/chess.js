/**
 * 
 */
 console.log("hello chess");

$(document).ready(function() {
    let counterGame = parseInt($("#time")[0].innerText);
    let counterMove = parseInt($("#time_move")[0].innerText);
    let interval = setInterval(function() {
        counterGame++;
        counterMove++;

        $('#time').text(counterGame);
        $('#time_move').text(counterMove);
    }, 1000);
});



let board = $("#board");
//time elasped



/*board.find('.figure')
    .draggable({
        revert: 'invalid',
        containment: board
    })
board.find('.figure').droppable({
    drop: function(ev, ui) {
        var dropped = ui.draggable;
        var droppedOn = $(this);
        $(droppedOn).droppable("disable");
        $(dropped).parent().droppable("enable");
        $(dropped).detach().css({top: 0, left: 0}).appendTo(droppedOn);
    }
});*/

//board.find('.figure').not('td:empty').droppable("disable");


    $(function(e) {
    	
        let turnText =  $('#player-turn').html();

        $('.figure').draggable({
            containment: board,
            zIndex: 1,
            snap: ".cell", 
            revert: 'invalid'
        });

        $('.cell').droppable({
        	 accept: function (el) {
                 return el.hasClass(turnText.toLowerCase());
             },
            tolerance: 'intersect',	
            over: function () {
                $(this).css("background-color", "green");
                console.log($(this).attr('class'));
            },
            out: function () {
                $(this).css("background-color", "")
            },
            drop: function (event ,ui) {
             	 let dropped = ui.draggable;
             	 let droppedOn = $(this);

                let gameId = $(location).attr('href').split('/')[5]; // http://localhost:8080/game/{id}

                let deleteUrl;
/*
                if($(this).attr('class').indexOf('disabled') >0) {
                    deleteUrl = '/game/delete/' + gameId + '/' + $(this).children().attr('data-id');
                    $(this).children().remove();
                    
                    $.ajax({
                        url: deleteUrl, success: function (result) {
                            console.log("Piece killed");
                        }
                    });
                }*/
                const oldPawnId = $(droppedOn).children();
                const oldClass =$(this).attr('class').indexOf('disabled');
                
                $(droppedOn).css("background-color", "");
                $(this).addClass('disabled');
                $(dropped).parent().removeClass('disabled');
                $(dropped).parent().droppable("enable");
                $(dropped).detach().css({top: 0, left: 0}).appendTo(droppedOn);
                let newPawnId = $(dropped).attr('data-id');


                let x = $(droppedOn).attr('data-x');
                let y = $(droppedOn).attr('data-y');

                let moveUrl = '/game/move/' + gameId + '/' +newPawnId + '/' + x + '/' + y;



                if (oldClass > 0) {
                    deleteUrl = '/game/move/' + gameId + '/' + newPawnId + '/' + oldPawnId.attr('data-id');
                    oldPawnId.remove();
                    window.location.href = deleteUrl;
                } else {
                	console.log("move");
                    window.location.href = moveUrl;
                }
            }
        });
    });
    
    if ($(location).attr('href').split('/')[4] === "promote") {
        $('#promotionModal').modal('toggle');
    }
    
    $(function () {
    if ($('#mate').html() != null && $('#winner').html() == null) {
        let currentPlayer = $('#player-turn').html();
        let winner = (currentPlayer === "Black" ? $('#Player1').text() : $('#Player2').text());
        let looser = (currentPlayer === "Black" ? $('#Player2').text() : $('#Player1').text());
        let gameId = $(location).attr('href').split('/')[5]; // http://localhost:8080/game/play/{id}
        window.location.href = '/game/endgame/' + gameId + '/' + winner + '/' + looser;
    }
});
    
    function leaveGame() {
    let gameId = $(location).attr('href').split('/')[5]; // http://localhost:8080/game/play/{id}
    window.location.href = '/game/resigning/' + gameId;
}
    

console.log("js fully loaded")

            /*    $.ajax({
                    url: moveUrl, success:function (result) {
                        console.log("move");
                        if (turnText === 'Black') {
                            $('#player-turn').text(turnText.replace(turnText, "White"))
                        } else if (turnText === 'White') {
                            $('#player-turn').text(turnText.replace(turnText, "Black"))
                        }
                    }
                })*/