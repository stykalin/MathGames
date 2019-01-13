<html>
<head>
    <title>MathMaze</title>
    <style type="text/css">
    BODY {
        text-align:center;
    }
    TABLE {
        border-collapse: collapse;
        margin-left:auto;
        margin-right:auto;
    }
    TD {
        padding: 3px;
        border: 1px solid black;
        text-align: center;
        width:30px;
        height:30px;
        font-size: 10px;
    }
    </style>
    <script>
		function printDiv(divName){
			var printContents = document.getElementById(divName).innerHTML;
			var originalContents = document.body.innerHTML;
			document.body.innerHTML = printContents;
			window.print();
			document.body.innerHTML = originalContents;
		}
	</script>
</head>
<body>
<div id='printable'>
    <h3>Математический лабиринт</h3>
<p>Пройди лабиринт закрашивая ячейки, где ${sign} = ${result}</p>
<table>
    <#list table as row>
    <tr>
        <#list row as cell><td>${cell} </#list>
    </#list>
</table>
</div>
<br>
<button onclick="printDiv('printable')">Распечатать</button>
</body>
</html>