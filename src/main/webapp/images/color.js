//Made by mat <mat100_at_ifrance.com> remplace _at_ with @

var height1 = 10;//define the height of the color bar
var pas = 28;// define the number of color in the color bar
var width1=Math.floor(-2/15*pas+6);//define the width of the color bar here automatic ajust for subsilver template.
var text1=s_help.substring(0,search(s_help,"="));
var text2=s_help.substring(search(s_help,"]"),search(s_help,"/"));

var base_hexa = "0123456789ABCDEF";

function dec2Hexa(number) {
   return base_hexa.charAt(Math.floor(number / 16)) + base_hexa.charAt(number % 16);
}

function RGB2Hexa(TR,TG,TB) {
   return "#" + dec2Hexa(TR) + dec2Hexa(TG) + dec2Hexa(TB);
}

col = new Array;
col[0] = new Array(255,0,255,0,255,-1);
col[1] = new Array(255,-1,255,0,0,0);
col[2] = new Array(0,0,255,0,0,1);
col[3] = new Array(0,0,255,-1,255,0);
col[4] = new Array(0,1,0,0,255,0);
col[5] = new Array(255,0,0,0,255,-1);
col[6] = new Array(255,-1,0,0,0,0);

function rgb(pas,w,h,text1,text2) {
	init();
   for (j=0;j<6+1;j++) {
      for (i=0;i<pas+1;i++) {
         r = Math.floor(col[j][0]+col[j][1]*i*(255)/pas);
         g = Math.floor(col[j][2]+col[j][3]*i*(255)/pas);
         b = Math.floor(col[j][4]+col[j][5]*i*(255)/pas);
         codehex = r + '' + g + '' + b;
         document.write('<td bgColor=\"' + RGB2Hexa(r,g,b) + '\"' 
         + 'onClick=\"bbfontstyle(\'[color=\' + this.bgColor + \']\',\'[/color]\');document.getElementById(\'colorused\').bgColor = this.bgColor;\" '
         + 'onmouseover=\"document.getElementById(\'colorused1\').bgColor = this.bgColor;this.style.cursor=\'pointer\'\" '
         + 'title=\"'+text1+ RGB2Hexa(r,g,b) + ']'+text2+'color]\" '
         + 'width=\"'+w+'\" height=\"'+h+'\">');
         if (is_ie)
         document.write('<IMG height='+h+' src="templates/subSilver/images/spacer.gif" width='+w+' border=0>');
         document.writeln('</TD>');
      }
   }
}

function search(text,caract) {
   for(i=0;i<text.length;i++) {
      if (caract == text.substring(i,i+1))
      return i+1;
   }
}

function init() {
	images = document.getElementsByName("color_bar");
	for (var i = 0; i < images.length; i++)
      images[i].height=height1;
}