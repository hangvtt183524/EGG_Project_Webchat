/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
        function showResult(x)
{
            var xhttp = new XMLHttpRequest() || ActiveXObject();
    //Bat su kien thay doi trang thai cuar request
            xhttp.onreadystatechange = function() {
            //Kiem tra neu nhu da gui request thanh cong
                 if (this.readyState == 4 && this.status == 200) {
                          if (this.responseText == "true") 
                              document.getElementById("list-participants").innerHTML +=
                                  "<li><a id=\"" + x.id + "\">"
                                      + "<i class=\"fa fa-circle text-success\"></i>"
                                      + x.textContent
                                  +"</a></li>";
                                  
                } 
            }
        //cau hinh request
            xhttp.open('POST', 'AddMemberIntoRoom', true);
    //cau hinh header cho request
            xhttp.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
    //gui request
            xhttp.send('member_id='+x.id);
}
