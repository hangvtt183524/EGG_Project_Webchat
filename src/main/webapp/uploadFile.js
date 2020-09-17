/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
      $(document).ready(function(){
          $('#upload-input').on('change', function(){
              $('#form').submit();
          });
      });
      
            $(document).ready(function() {
    $('form').submit(function(event) {
        event.preventDefault();
 
        // Calling AJAX
        $.ajax({
            url : $(this).attr('action'),
            type : $(this).attr('method'),
            data : new FormData(this),
            contentType : false,
            cache : false,
            processData : false,
            success : function(response) {
                $('#messageText').val(response);
                $('#btn-sendMessage').click();
            }
        });
 
        return false;
    });
 
});

