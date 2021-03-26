class Mensaje {
	constructor(texto, hora) {
        this.texto = texto;
        var date = new Date();
        if(hora!=null)  date = new Date(hora);
        this.hora = date.getDate()+"/"+(date.getMonth()+1)+"/"+date.getFullYear()+" "+date.getHours()+":"+date.getMinutes()+":"+date.getSeconds();
    }
}