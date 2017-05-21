/**
 * Created by ASUS on 5/18/2017.
 */
var stage;
var isMouseDown;
var currentShape;
var oldMidX, oldMidY, oldX, oldY;
var txt;

function init() {
    txt = new createjs.Text("Click and Drag to Draw", "36px Arial", "#777777");
    txt.x = 300;
    txt.y = 200;
    var canvas = document.getElementById('myCanvas');
    stage = new createjs.Stage(canvas);
    stage.autoClear = true;
    stage.onMouseDown = handleMouseDown;
    stage.onMouseUp = handleMouseUp;

    createjs.Touch.enable(stage);

    stage.addChild(txt);
    stage.update();
    createjs.Ticker.addEventListener(window);
}

function stop() {
    createjs.Ticker.removeEventListener(window);
}

function tick() {
    if (isMouseDown) {
        var pt = new createjs.Point(stage.mouseX, stage.mouseY);
        var midPoint = new createjs.Point(oldX + pt.x>>1, oldY+pt.y>>1);
        currentShape.graphics.moveTo(midPoint.x, midPoint.y);
        currentShape.graphics.curveTo(oldX, oldY, oldMidX, oldMidY);

        oldX = pt.x;
        oldY = pt.y;

        oldMidX = midPoint.x;
        oldMidY = midPoint.y;

        stage.update();
    }
}

function handleMouseDown() {
    isMouseDown = true;
    stage.removeChild(txt);

    var s = new createjs.Shape();
    oldX = stage.mouseX;
    oldY = stage.mouseY;
    oldMidX = stage.mouseX;
    oldMidY = stage.mouseY;
    var g = s.graphics;

    console.log(g.beginStroke);
    var thickness = Math.random() * 30 + 10 | 0;
    g.setStrokeStyle(thickness + 1, 'round', 'round');

    var r = Math.random()*255 | 0;
    var g2 = Math.random()*255 | 0;
    var b = Math.random()*255 | 0;

    var color = createjs.Graphics.getRGB(r,g2,b);
    g.beginStroke(color);
    stage.addChild(s);
    currentShape = s;
}

function handleMouseUp() {
    isMouseDown = false;
}
