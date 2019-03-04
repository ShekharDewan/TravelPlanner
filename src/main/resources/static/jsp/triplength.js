var current = new Date();
y1 = current.getFullYear();
m1 = current.getMonth() + 1;
d1 = current.getDate();
var date = m1 + "/" + d1 + "/" + y1;
current = new Date(date);

var newDate = begin;
var start = new Date(newDate);
y1 = start.getFullYear();
m1 = start.getMonth() + 1;
d1 = start.getDate();
start = m1 + "/" + d1 + "/" + y1;

var newDate = conclude;
var end = new Date(newDate);
y2 = end.getFullYear();
m2 = end.getMonth() + 1;
d2 = end.getDate();
end = m2 + "/" + d2 + "/" + y2;

var diff = end.getTime() - start.getTime();
var timeDiff = Math.abs(diff);
var difference = Math.ceil(timeDiff / (1000 * 3600 * 24)); 

if (diff < 0) {
  var message = "memento trip";
}
else{
	var message = "Duration of " + difference + " days.";
}
var ele = document.getElementsByClassName("duration");
var len = ele.length;

ele[len-1].innerHTML = message;
