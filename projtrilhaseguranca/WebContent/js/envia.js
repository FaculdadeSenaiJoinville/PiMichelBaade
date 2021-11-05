function ValidaDados(){
	
	console.log(senhaembase64);
	var senhaembase64 = btoa(document.frmLogin.senha.value);
	console.log(senhaembase64);
	document.frmLogin.senha.value = senhaembase64;
	
	return true;
	
}