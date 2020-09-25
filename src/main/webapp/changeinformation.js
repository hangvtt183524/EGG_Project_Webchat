/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
$(document).ready(function(){
          $('#update').on('click', function(){
              $.ajax({
            url : 'changeInformation',
            type : 'post',
            data : {command: 'not-avatar',
                    firstname: $('#ip1').attr('value'),
                    lastname:  $('#ip2').attr('value'),
                    email:     $('#ip3').attr('value'),
                    password:  $('#ip4').attr('value')},
            success : function(response) {
                    $('#user-name').html("<b>" + response + "</b>");
            }
            
        });
          });
      });

