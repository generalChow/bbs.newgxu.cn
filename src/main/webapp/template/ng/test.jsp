<@base.manifest baseUrl="/resources" title="hello macro" date="2013-02-27">
<body>
	<div data-role="page">
		<div data-role="header">
			<h1>hi, my love!</h1>
		</div>
		<div data-role="content">
			<a href="javascript:click()" data-role="button">click</a>
		</div>
	</div>
</body>
<script>
	function click() {
		alert("hello!");
	}
</script>
</@base.manifest>