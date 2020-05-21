// var re = /(https:\/\/item.jd.com\/[0-9]+.html).*/
// var url = "https://item.jd.com/10019917.html#crumb-wrap"
// //  /https:\/\/item.jd.com\/\/[0-9]+.html/
// var id = url.replace(re, "$1")
// //console.log(id)

var url = 'https://item.jd.com/10019917.html#crumb-wrap'
var re = /https:\/\/item\..*jd.com(\/[a-zA-Z]*)\/([0-9])+.html.*/
var id = url.replace(re, "$1")
console.log(id)