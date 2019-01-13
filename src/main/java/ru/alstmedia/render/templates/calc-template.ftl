<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>Примеры</title>
    <style>
	.button {
		background-color: #4CAF50; /* Green */
		border: none;
		color: white;
		text-align: center;
		text-decoration: none;
		display: inline-block;
		font-size: 16px;
		margin: 6px 4px;
		cursor: pointer;
	}
	td {
		width: 140px;
	}
	h3{
		margin: 15px 15px;
	}
</style>

</head>

<body>
<div id="printMe">
    <h3 align="center">Реши примеры</h3>
    <table id="myTable" align="center" border="1">
        <tbody >
        <tr>
            <td id="col1"></td>
            <td id="col2"></td>
            <td id="col3"></td>
            <td id="col4"></td>
        </tr>
        </tbody>
    </table>

</div>
<div id="date"></div>

<div align="center">
    <button class="button" onclick="refreshExamples()">Обновить примеры</button>
    <button class="button" onclick="printDiv('printMe')">Распечатать</button>
</div>


<script type="text/javascript">
		var col1MaxNum = 10;
		var col2MaxNum = 20;
		var col3MaxNum = 30;
		var col4MaxNum = 40;
		refreshExamples();

		function refreshExamples(){
			prepExamples('col1', ${col1MaxNum});
			prepExamples('col2', ${col2MaxNum});
			prepExamples('col3', ${col3MaxNum});
			prepExamples('col4', ${col4MaxNum});
		}

		function prepExamples(_id, _maxNum){
			var list = "";
			for (var i = 0; i < 10; i++) {
				list += makeExample(_maxNum);
			}
			document.getElementById(_id).innerHTML = list;
		}

		function makeExample(_maxNum) {
			var maxNum = _maxNum;
			var sing = getNum(2);
			var a = getNum(maxNum);
			var b = getNum(maxNum);
			var ex;

			switch (sing) {
				case 0:
				ex = "<h3>" + a + " + " + b + " =<\/h3>";
				break;
				case 1:
				if((a - b) < 0){
					ex = "<h3>" + b + " - " + a + " =<\/h3>";
				}else {
					ex = "<h3>" + a + " - " + b + " =<\/h3>";
				}
				break;
				case 2:
				ex = a + " * " + b + " =<br \/>";
				break;
				case 3:
				if(b == 0){
					while(b == 0){
						b = getNum(maxNum);
					}
				}else if((a/b) < 0){
					ex = b + " / " + a + " =<br \/>";
				}else {
					ex = a + " / " + b + " =<br \/>";
				}
			}
			return ex;
		}

		function getNum(max) {
			return Math.floor((Math.random() * max) + 0);
		}

		function printPage(){
			window.print();
		}

		function printDiv(divName){
			var printContents = document.getElementById(divName).innerHTML;
			var originalContents = document.body.innerHTML;

			document.body.innerHTML = printContents;

			window.print();

			document.body.innerHTML = originalContents;
		}

	</script>
</body>
</html>