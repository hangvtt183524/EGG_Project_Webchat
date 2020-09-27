/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
// handle when user search people, filter by name
   function searchPeople() {
    var input, filter, ul, li, a, i, txtValue;
    input = document.getElementById("myInput");
    filter = input.value.toUpperCase();
    ul = document.getElementById("myUL");
    li = ul.getElementsByTagName("li");
    for (i = 0; i < li.length; i++) {
        a = li[i].getElementsByTagName("a")[0];
        txtValue = a.textContent || a.innerText;
        if (txtValue.toUpperCase().indexOf(filter) > -1) {
            li[i].style.display = "";
        } else {
            li[i].style.display = "none";
        }
    }
}

// add member to room. All member will show in room-list-member
function addToRoom(x)
{
    
    var xhttp = new XMLHttpRequest() || ActiveXObject();
    //Bat su kien thay doi trang thai cuar request
    xhttp.onreadystatechange = function() {
            //Kiem tra neu nhu da gui request thanh cong
            if (this.readyState == 4 && this.status == 200) {
                if (this.responseText != 'false' && this.responseText != null)
                    document.getElementById("div21").innerHTML +=
                             "<div class=\"people\" id=\"user-" + this.responseText + "\">"
                             +     "<img src=\"" + x.firstElementChild.firstElementChild.src + "\" class=\"rounded-circle my-avatar\">"
                             +     "<span class=\"name\" style=\"color:rgb(100, 100, 100);\">" + x.firstElementChild.textContent + "</span>"
                             +     "<button type=\"button\" class=\"close kick\">&times;</button>"
                             + "</div>";
            }
        }
        //cau hinh request
    xhttp.open('POST', 'AddMemberIntoRoom', true);
    //cau hinh header cho request
    xhttp.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
    //gui request
    xhttp.send('member_id=' + x.id);
}
