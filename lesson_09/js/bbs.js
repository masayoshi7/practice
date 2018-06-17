//リセットボタンをjavascriptで疑似的に再現
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

$(".modal-open").click(
    function()
    {
        $(this).blur();
        if($("#modal-overlay")[0]) return false ;
        $("body").append('<div id="modal-overlay"></div>');
        $("#modal-overlay").fadeIn();
        $(".modal-content").fadeIn();
    }
);

$("#modal-overlay,.modal-close").unbind().click(function(){
    $(".modal-content,#modal-overlay").fadeOut("slow",function(){
        $("#modalErrorMsg").html("");
        $("#modal-overlay").remove();
    });
});

function getValue(temp)
{
    var parentJsObject = $(temp).parent().parent().parent();
    id = parentJsObject.find(".getId").val();
    name = parentJsObject.find(".getName").html();
    title = parentJsObject.find(".getTitle").html();
    comment = parentJsObject.find(".getComment").html().replace(/<br>/g, "");
    eMail = parentJsObject.find(".getEMail").attr("href");

    valueUpdate();
}

function valueUpdate()
{

    $("#id").val(id);
    $("#uName").val(name);
    $("#uTitle").val(title);
    $("#uComment").val(comment);
    if (eMail != undefined) {
        $("#uEmail").val(eMail.substring(eMail.indexOf(":") + 1, eMail.length));
    }
    else {
        $("#uEmail").val("");
    }
}

if (updateErrorFlag != null) {

    $("body").append('<div id="modal-overlay"></div>');
    $("#modal-overlay").fadeIn("fast");
    $(".modal-content").fadeIn("fast");
    var errorMsg = $("#errorMsg").html();
    $("#errorMsg").html("");
    $("#modalErrorMsg").html(errorMsg);
}
