/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

$(document).ready(function(){
    $('#quitRoom').click(function(){
        $.ajax({
            type: 'post',
            data: {command: 'quit'},
            url: 'OutRoom',
            success: function(result)
            {
                if (result == 'false') alert("You are this Room's Admin. You are allowed to quit unless delete the room!");
                else 
                {
                    $('#room-' + result).css("display", "none");
                }
            }
        });
    });
});
