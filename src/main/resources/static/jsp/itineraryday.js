var itinerary = itin;
var itinDate = new Date(itinerary);
y = itinDate.getFullYear();
m = itinDate.getMonth() + 1;
d = itinDate.getDate();
var date = m + "/" + d + "/" + y;
itinDate = new Date(date);

var newDate = begin;
var start = new Date(newDate);
y = start.getFullYear();
m = start.getMonth() + 1;
d = start.getDate();

date = m + "/" + d + "/" + y;
var date2 = new Date(date);
var diff = date2.getTime() - itinDate.getTime();
var timeDiff = Math.abs(diff);
var difference = Math.ceil(timeDiff / (1000 * 3600 * 24)); 

difference++;
var message = "Day " + difference + ". ";

var ele = document.getElementsByClassName("itinday");
var len = ele.length;

ele[len-1].innerHTML = message;
