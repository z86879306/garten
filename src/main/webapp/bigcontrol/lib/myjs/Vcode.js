function Vcode(option) {
    this._init(option);
}
Vcode.prototype = {
    _init: function (option) {
        this.id = option.id;
        this.w = option.w || 100;
        this.h = option.h || 30;
        this.code = '';
        var self = this;
        var canvas = document.getElementById(this.id),
            ctx = canvas.getContext('2d');
        canvas.width = this.w;
        canvas.height = this.h;
        canvas.style.cursor = 'pointer';
        this.getVcode(ctx);
        canvas.addEventListener('click',function () {
            self.getVcode(ctx);
        });
    },
    randomNum: function ( min , max ) {
        var num = Math.floor(Math.random() * ( max - min ) + min);
        return num;
    },
    getAllLetter: function () {
        var str = "a,b,c,d,e,f,g,h,i,j,k,l,m,n,o,p,q,r,s,t,u,v,w,x,y,z,A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R,S,T,U,V,W,X,Y,Z";
        return str.split(',');
    },
    getRandomColor: function(min,max){
        var r = this.randomNum(min,max),
            g = this.randomNum(min,max),
            b = this.randomNum(min,max);
        return 'rgb' + '(' + r +','+ g + ',' + b + ')';
    },
    getVcode: function (ctx) {
        this.code = '';
        ctx.textBaseline = 'middle';
        ctx.beginPath();
        ctx.fillStyle = this.getRandomColor(180,240);
        ctx.fillRect(0,0,this.w,this.h);
        var letters = this.getAllLetter();
        for(var i = 1; i <= 4; i++){
            ctx.beginPath();
            var txt = letters[this.randomNum(0,letters.length)];
            this.code += txt;
            ctx.font = this.randomNum(this.h / 2 , this.h) + 'px SimHei';
            ctx.fillStyle = this.getRandomColor(50,160);
            ctx.shadowOffsetX = this.randomNum(-2, 2);
            ctx.shadowOffsetY = this.randomNum(-2, 2);
            ctx.shadowBlur = this.randomNum(-2, 2);
            ctx.shadowColor = "rgba(0, 0, 0, 0.3)";
            var x = this.w / 5 * i,
                y = this.h / 2,
                deg = this.randomNum(-25,25);
            ctx.translate(x,y);
            ctx.rotate(deg * Math.PI /180);
            ctx.fillText(txt, 0, 0);
            ctx.rotate(-deg * Math.PI /180);
            ctx.translate(-x , -y);
        }
    },
    passCode: function (code) {
        var code = code.toLowerCase(),
            v_code = this.code.toLowerCase();
        if(code === v_code){
            return true;
        }else {
            return false;
        }
    }
}