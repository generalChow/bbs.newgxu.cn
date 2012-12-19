Element.extend({classNames:function () {
	return this.className.clean().split(/\s+/);
}, visible:function (_2d) {
	return this.getStyle("display") != "none";
}, hide:function () {
	this.setStyle("display", "none");
	return this;
}, show:function () {
	this.setStyle("display", "");
	return this;
}, toggle:function () {
	this[this.visible() ? "hide" : "show"]();
	return this;
}, within:function (x, y) {
	this.offset = [this.getLeft(), this.getTop()];
	return (y >= this.offset[1] && y < this.offset[1] + this.offsetHeight && x >= this.offset[0] && x < this.offset[0] + this.offsetWidth);
}, getOpacity:function () {
	var _30;
	if (_30 = this.getStyle("opacity")) {
		return parseFloat(_30);
	}
	if (_30 = (this.getStyle("filter") || "").match(/alpha\(opacity=(.*)\)/)) {
		if (_30[1]) {
			return parseFloat(_30[1]) / 100;
		}
	}
	return 1;
}, disable:function () {
	$A(this.getElementsByTagName("*")).each(function (el) {
		$(el);
		if (["select", "input", "textarea"].test(el.getTag())) {
			el.blur();
			el.disabled = true;
		}
	});
}, enable:function () {
	$A(this.getElementsByTagName("*")).each(function (el) {
		$(el);
		if (["select", "input", "textarea"].test(el.getTag())) {
			el.disabled = "";
		}
	});
}});
Element.visible = function (el) {
	return $(el).visible();
};
Element.show = function () {
	for (var i = 0; i < arguments.length; i++) {
		$(arguments[i]).show();
	}
};
Element.hide = function () {
	for (var i = 0; i < arguments.length; i++) {
		$(arguments[i]).hide();
	}
};
Element.toggle = function () {
	for (var i = 0; i < arguments.length; i++) {
		$(arguments[i]).toggle();
	}
};
