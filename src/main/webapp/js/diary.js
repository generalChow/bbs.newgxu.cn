function popUpWindow(url,title){
	var popUpWin=0;
	if(popUpWin){
		if(!popUpWin.closed) {
			popUpWin.close();
		}
	}
	window.open(url, title,'toolbar=no,location=no,directories=no,status=no,menubar=no,scrollbar=yes,resizable=no,copyhistory=yes,width=830,height=660');
}
