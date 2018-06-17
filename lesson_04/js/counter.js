$(function(){
    $('#resetBotton').click(function() {
        if (!confirm('アクセスカウンタを0に戻しますか？')) {
            return false;
        } else {
            $.ajax({
                dataType: 'json',
                type:     'POST',
                url:      'Ajax.php',
                success:function(data)
                {
                    document.getElementById('counter').innerHTML = data;
                },
                error:function(XMLHttpRequest, textStatus, errorThrown)
                {
                    alert('error');
                }
            });
        }

        // 非同期的に行われている処理を処理終了後に行うよう設定
        reset().done(function(result){})
        .fail(function(result){});
    })
});
