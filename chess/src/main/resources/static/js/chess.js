console.log("hello chess");

let board = $("#board");

board.find('.figure')
    .draggable({
        revert: 'invalid',
        containment: board
    })
    .on('dragstart', (ev, ui) => {
     
    })
    .on('dragstop', (ev, ui) => {
   
    })
    .on('drop', (ev, ui) => {
       
    });