/**
 * 
 */
 console.log("hello chess");

let board = $("#board");

board.find('.figure')
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
});

board.find('.figure').not('td:empty').droppable("disable");


    $(function() {
        $('.figure').draggable({
            containment: board,
            zIndex: 1,
            snap: ".cell", 
            revert: function() {
                if($(this).hasClass('drag-revert')) {
                    $(this).removeClass('drag-revert');
                    return true;
                }
            }
        })

        $('.cell').droppable({
            accept: ".figure",
            tolerance: 'intersect',
            over: function () {
                $(this).css("background-color", "green");

            },
            out: function () {
                $(this).css("background-color", "")
            },
            drop: function (event ,ui) {
             	let dropped = ui.draggable;
             	 let droppedOn = $(this);

                let gameId = $(location).attr('href').split('/')[5]; // http://localhost:8080/game/{id}

                let deleteUrl;

                if($(this).attr('class').indexOf('disabled') >0) {
                    deleteUrl = '/game/delete/' + gameId + '/' + $(this).children().attr('data-id');
                    $(this).children().remove();
                    
                    $.ajax({
                        url: deleteUrl, success: function (result) {
                            console.log("Piece killed");
                        }
                    });
                }
                $(droppedOn).css("background-color", "");
                $(this).addClass('disabled');
                $(dropped).parent().removeClass('disabled');
                $(dropped).parent().droppable("enable");
                $(dropped).detach().css({top: 0, left: 0}).appendTo(droppedOn);


                let pawnId = $(droppedOn).children().attr('data-id');
                 let x = $(droppedOn).attr('data-x');
                let y = $(droppedOn).attr('data-y');

                let moveUrl = '/game/move/' + gameId + '/' + pawnId + '/' + x + '/' + y;

                let turnText = $('#player-turn').html();

                $.ajax({
                    url: moveUrl, success:function (result) {
                        console.log("move");
                        if (turnText === 'Black') {
                            $('#player-turn').text(turnText.replace(turnText, "White"))
                        } else if (turnText === 'White') {
                            $('#player-turn').text(turnText.replace(turnText, "Black"))
                        }
                    }
                })
            }
        })
    })

console.log("js fully loaded")