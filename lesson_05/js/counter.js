$(function(){
    $("#resetBotton").click(function() {
        if (!confirm("アクセスカウンタを0に戻しますか？")) {
            return false;
        }
        else {
            $.ajax({
                type: "POST",
                dataType:"json",
                url:"Ajax.php",
                success:function(data)
                {
                    document.getElementById("counter").innerHTML = data;
                },
                error:function(XMLHttpRequest, textStatus, errorThrown)
                {
                    alert("error");
                }
            });
        }

        // 非同期的に行われている処理を処理終了後に行うよう設定
        reset().done(function(result) {})
        .fail(function(result) {});
    })

    // counter.phpでフラグが立っている場合は吹き出しを表示する
    if (flag == "yes") {

        // カウンター要素のタイトルにメッセージを設定
        document.getElementById("rightBea").title = "一日の間に二回以上はカウントされないよ";
        document.getElementById("leftBea").title = "ちなみにあなたは本日" + count + "回訪問してるよ";

        // balloon.jsによって表示
        $("#rightBea").showBalloon({ position: "right"});
        $("#leftBea").showBalloon({ position: "left"});
    }
});
