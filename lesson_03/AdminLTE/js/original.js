function searcher()
{
  var text = document.getElementById("mozyu").value;
  var targets = document.getElementsByClassName("name");
  var add_text = document.getElementById("add-text");

  if(!(text === null || text === ""))
  {
    add_text.innerHTML = "現在の検索ワード = " + text;
    for(var i = 0; i < targets.length; i++)
    {
      var target = targets[i].innerHTML;
      if(!(target.includes(text)))
      {
        targets[i].parentNode.style.display = "none";
      }
      else
      {
        targets[i].parentNode.style.display = "";
      }
    }
  }
  else
  {
      add_text.innerHTML = "検索キーワードがありません";
      for(var i = 0; i < targets.length; i++)
      {
        targets[i].parentNode.style.display = "";
      }
  }
}

$("#mozyu").on("click keydown", function(e)
{
  if(e.keyCode === 13)
  {
    searcher();
    var key = document.getElementById("key");
    key.click();
    return false;
  }
});
