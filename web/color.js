function setcolor(color){
  var alist= document.querySelectorAll('a');
  var i=0;
  while(i<alist.length){
    alist[i].style.color=color;
    i=i+1;
}
}
function nightdayhandler(self){
  var target =document.querySelector('body');
  if(self.value==='night'){
    target.style.backgroundColor='black';
    target.style.color='white';
    self.value='day';
    setcolor('powderblue');
  }else{
    target.style.backgroundColor='white';
    target.style.color='black';
    self.value='night';
    setcolor('blue');
  }
}
