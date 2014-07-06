function ratingDialogStartFunction() {
	myVar = setTimeout(function() {
		ratingDialog.hide();
	}, 3000);
}

function ratingDialogStopFunction() {
	clearTimeout(myVar);
}
