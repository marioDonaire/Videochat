define(['knockout', 'appController', 'ojs/ojmodule-element-utils', 'accUtils'],
		function(ko, app, moduleUtils, accUtils) {

	function ChatViewModel() {
		var self = this;
		
		this.user = app.user;
		
		self.recipient = ko.observable();

		self.chat = ko.observable(new Chat(ko));
		
		self.videoChat = ko.observable(new VideoChat(ko));

		self.estadoChatDeTexto = self.chat().estado;
		self.estadoSignaling = self.videoChat().estado;
		self.errorChatDeTexto = self.chat().error;
		self.errorSignaling = self.videoChat().error;

		// Header Config
		self.headerConfig = ko.observable({'view':[], 'viewModel':null});
		moduleUtils.createView({'viewPath':'views/header.html'}).then(function(view) {
			self.headerConfig({'view':view, 'viewModel': app.getHeaderModel()})
		})

		self.connected = function() {
			accUtils.announce('Chat page loaded.');
			document.title = "Chat";

			getUsuariosConectados();			
		};

		function getUsuariosConectados2() {
			var data = {	
				url : "users/getUsuariosConectados",
				type : "get",
				contentType : 'application/json',
				success : function(response) {
					for (var i=0; i<response.length; i++) {
						var userName = response[i].name;
						var picture = response[i].picture;
						self.chat().addUsuario(userName, picture);
					}
				},
				error : function(response) {
					self.error(response.responseJSON.error);
				}
			};
			$.ajax(data);
		}
		
		function getUsuariosConectados() {
			var data = {	
				url : "users/getNombresUsuariosConectados",
				type : "get",
				contentType : 'application/json',
				success : function(response) {
					for (var i=0; i<response.length; i++) {
						var userName = response[i];
						var picture = ko.observable(null);
					}
						self.chat().addUsuario(userName,picture);	
				},
				error : function(response) {
					self.error(response.responseJSON.error);
				}
			};
			$.ajax(data);
		}
		
		function getFotoUsuario(userName) {
			var data = {
				url : "users/getFotoUsuario/"+userName,
				type : "get",
				contentType : 'application/json',
				success : function(response) {
					user.foto(response);
				},
				error : function(request, status, error) {
					console.log("Se ha producido un error al cargar la foto: "+error+' - '+status);
				}
			};
			$.ajax(data);
		}
		
		self.encenderVideoLocal = function() {
			self.videoChat().encenderVideoLocal();
		}
		
		self.crearConexion = function() {
			self.videoChat().crearConexion();
		}

		self.enviarOferta = function(destinatario) {
			self.videoChat().enviarOferta(destinatario.nombre);
		}
		
		self.disconnected = function() {
			self.chat().close();
		};

		self.transitionCompleted = function() {
			// Implement if needed
		};
	}

	return ChatViewModel;
}
);
