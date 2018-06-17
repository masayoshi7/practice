// モーダル関係
$(function() {
    $(".open").on("click", function() {
        $("#overlay, #modalWindow").fadeIn();
    });

    $("#close").on("click", function() {
        $("#overlay, #modalWindow").fadeOut();
    });

    $("#insertButton").on("click", function(){
        $("#insertForm").fadeIn();
    });

    locateCenter();
    $(window).resize(locateCenter);

    function locateCenter()
    {
        let w = $(window).width();
        let h = $(window).height();

        let cw = $("#modalWindow").outerWidth();
        let ch = $("#modalWindow").outerHeight();

        $("#modalWindow").css({
            "left": ((w - cw) / 2) + "px",
            "top": ((h - ch) / 4) + "px"
        });
    }
});

function getValue(temp)
{
    var parentJsObject = $(temp).parent().parent();
    id                 = parentJsObject.find(".id").val().trim();
    region             = parentJsObject.find(".region").html().trim();
    number             = parentJsObject.find(".number").html().trim();
    goods              = parentJsObject.find(".goods").html().trim();

    valueUpdate();
}

function valueUpdate()
{
    $("#id").val(id);
    $("#region").val(region);
    $("#number").val(number);
    $("#goods").val(goods);
}
