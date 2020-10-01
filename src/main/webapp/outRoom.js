/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
// handle when user want to quit room
$(document).ready(function(){
    
    $('#quitRoom').click(function(){
        // use ajax to send command to servlet, check if this user is the room's admin or not
        $.ajax({
            type: 'post',
            data: {command: 'quit'},
            url: 'OutRoom',
            success: function(result)
            {
                // if user is admin, couldn't quit
                if (result == 'false') alert("You are this Room's Admin. You are allowed to quit unless delete the room!");
                // if user is not admin, cound quit
                // room-name in chat-list will disappear
                else 
                {
                    $('#room-' + result).css("display", "none");
                    $('#insert-roomName1').html("");
                    // call function removeSocket on startChat.js
                    $('#removeSocket').click();
                    var event1 = document.getElementById("p1");
                    
                    event1.classList.toggle('col-9');
                    event1.classList.toggle('col-6');
                }
            }
        });
    });
    
    
    // handle if user want to delete this room
    $('#deleteRoom').click(function(){
        $.ajax({
            type: 'post',
            data: {command: 'delete'},
            url: 'OutRoom',
            success: function(result)
            {
                // if user is not admin of the room, can't delete
                if (result == 'false') alert("You are  not this Room's Admin. Can't delete the conversation!");
                else 
                {
                    $('#room-' + result).css("display", "none");
                    $('#insert-roomName1').html("");
                    $('#removeSocket').click();
                    var event1 = document.getElementById("p1");
                    
                    event1.classList.toggle('col-9');
                    event1.classList.toggle('col-6');
                }
            }
        });
    });

});
