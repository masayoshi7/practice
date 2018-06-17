var flag = getCookie("flag");
if (flag == -1) {
    document.cookie = "flag=yes";
} else {
    alert("再読み込みによるメール送信は行われません");
    location.href = "form.php";
}

/*
* クッキーが存在するか確認するメソッド
* 引数は探したいクッキーの名前を受け取る
* 同じような名前の場合想定外の動作をしそう
* 例abc,abc1
*/
function getCookie(target)
{
    var result  = -1;
    var cookies = document.cookie;
    var array   = cookies.split(";");
    for (i = 0; i < array.length; i++) {
        if (array[i].indexOf(target) != -1) {
            result = array[i];
        }
    }

    // 見つからない場合-1を返す
    return result;
}
