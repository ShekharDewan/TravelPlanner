var current = new Date();
y = current.getFullYear();
m = current.getMonth() + 1;
d = current.getDate();
var date = m + "/" + d + "/" + y;
current = new Date(date);

var newDate = begin;
var start = new Date(newDate);
y = start.getFullYear();
m = start.getMonth() + 1;
d = start.getDate();

date = m + "/" + d + "/" + y;
var date2 = new Date(date);
var diff = date2.getTime() - current.getTime();
var timeDiff = Math.abs(diff);
var difference = Math.ceil(timeDiff / (1000 * 3600 * 24)); 

if (diff < 0) {
  var message = difference + " days past";
}
else{
var message = "in " + difference + " days";
}
var ele = document.getElementsByClassName("remain");
var len = ele.length;

ele[len-1].innerHTML = message;
