package com.camc.common

/*
@Composable
fun Progress() {
    val sweepState = remember {
        mutableStateOf(0f)
    }
    val max = 100f
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(20.dp))
        ProgressBarView(sweepState)
        Spacer(modifier = Modifier.height(20.dp))
        Button(onClick = {
            sweepState.value = Random.nextInt(1 until 99) / max
        }) {
            Text(text = "按钮")
        }
    }
}

@OptIn(ExperimentalTextApi::class)
@Composable
private fun ProgressBarView(sweepState: MutableState<Float>) {
    val animAngle = animateFloatAsState(
        targetValue = sweepState.value * 360,
        animationSpec = tween(1000)
    )
    val animPercent = animateIntAsState(
        targetValue = (sweepState.value * 100).toInt(),
        animationSpec = tween(1000)
    )
    val textPercent = "${animPercent.value}%"
    val textPercentLayResult = rememberTextMeasurer().measure(
        text = AnnotatedString(textPercent),
        style = TextStyle(
            color = Color(96, 98, 172),
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold
        )
    )
    val textDesc = "出勤率"
    val textDescLayoutResult = rememberTextMeasurer().measure(
        AnnotatedString(textDesc),
        TextStyle(color = Color(178, 193, 209))
    )
    Canvas(modifier = Modifier.size(300.dp), onDraw = {
        val innerStrokeWidth = 10.dp.toPx()
        val radius = 120.dp.toPx()
        val outStrokeWidth = 17.dp.toPx()
        val canvasWidth = size.width
        val canvasHeight = size.height
        //内部圆
        drawCircle(
            Color(222, 228, 246),
            radius = radius,
            center = Offset(canvasWidth / 2, canvasHeight / 2),
            style = Stroke(innerStrokeWidth)
        )
        //圆弧进展
        drawArc(
            Color(46, 120, 249),
            startAngle = -90f,
            sweepAngle = animAngle.value,
            useCenter = false,
            size = Size(radius * 2, radius * 2),
            style = Stroke(outStrokeWidth, cap = StrokeCap.Round),
            topLeft = Offset(center.x - radius, center.y - radius)
        )
        val textPercentWidth = textPercentLayResult.size.width
        val textPercentHeight = textPercentLayResult.size.height
        //百分比文字
        drawText(
            textLayoutResult = textPercentLayResult,
            topLeft = Offset(
                canvasWidth / 2 - textPercentWidth / 2,
                canvasHeight / 2 - textPercentHeight
            ),
        )
        val textDescWidth = textDescLayoutResult.size.width
        val textDescHeight = textDescLayoutResult.size.height //用不着
        //出勤率
        drawText(
            textLayoutResult = textDescLayoutResult,
            topLeft = Offset(
                canvasWidth / 2 - textDescWidth / 2,
                canvasHeight / 2
            ),
        )
    })
}*/
