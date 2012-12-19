var newgxu = window.newgxu || new Object();
newgxu.DropdownMenu = new Class({	
	initialize: function(element){
		$A($(element).childNodes).each(function(el){
			if(el.nodeName.toLowerCase() == 'li'){
				$A($(el).childNodes).each(function(el2){
					if(el2.nodeName.toLowerCase() == 'ul'){
						$(el2).hide();
						el.addEvent('mouseover', function(){
							el2.show();
							return false;
						});
	
						el.addEvent('mouseout', function(){
							el2.hide();
						});
						new newgxu.DropdownMenu(el2);
					}
				});
			}
		});
		return this;
	}
});