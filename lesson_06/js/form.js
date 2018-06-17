
// flagcookieの削除
var date1 = new Date();

// 1970年1月1日00:00:00の日付データをセットする
date1.setTime(0);
document.cookie = "flag=;expires="+date1.toUTCString();

// 名前入力欄の文字数チェック
$("#nickName").each(function(){
    $(this).bind("keyup", nameCheck(this));
});
function nameCheck(elm)
{
    var old = elm.value;
    var add;
    return function()
            {
                if (old != (add = elm.value)) {
                    if (!(add.match(/^.{0,20}$/i))) {
                        elm.title = "20文字以内で入力してください";
                        $("#nickName").showBalloon({ position: "right"});
                    } else {
                        $("#nickName").hideBalloon();
                    }
                }
            }
}

// リセットボタンをjavascriptで疑似的に再現
function clearFormAll()
{
    for (var i = 0; i < document.forms.length; ++i) {
        clearForm(document.forms[i]);
    }
}

function clearForm(form)
{
    for　(var i = 0; i < form.elements.length; ++i) {
        clearElement(form.elements[i]);
    }
}

function clearElement(element)
{
    switch(element.type) {
        case "hidden":
        case "submit":
        case "reset":
        case "button":
        case "image":
        case "file":
            return;
        case "text":
        case "password":
        case "textarea":
            element.value = "";
            return;
        case "checkbox":
        case "radio":
            element.checked = false;
            return;
        case "select-one":
        case "select-multiple":
            element.selectedIndex = 0;
            return;
        }
}
