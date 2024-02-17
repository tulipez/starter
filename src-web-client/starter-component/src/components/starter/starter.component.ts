import { html, LitElement } from 'lit';
import { state } from 'lit/decorators.js';
import { provide } from '@lit/context';
import '@shoelace-style/shoelace/dist/themes/light.css';
import '@shoelace-style/shoelace/dist/themes/dark.css';
import '@shoelace-style/shoelace/dist/components/button/button.js';
import '@shoelace-style/shoelace/dist/components/icon/icon.js';
import '@shoelace-style/shoelace/dist/components/input/input.js';
import '@shoelace-style/shoelace/dist/components/rating/rating.js';
import '@shoelace-style/shoelace/dist/components/alert/alert.js';
import '@shoelace-style/shoelace/dist/components/avatar/avatar.js';
import '@shoelace-style/shoelace/dist/components/menu/menu.js';
import '@shoelace-style/shoelace/dist/components/menu-item/menu-item.js';
import '@shoelace-style/shoelace/dist/components/divider/divider.js';
import { registerIconLibrary } from '@shoelace-style/shoelace/dist/utilities/icon-library.js';

import styles from './starter.styles.js';
// import { IS_MOBILE, MatchMediaController } from '../../controllers/MatchMedia.js';
import { UserService, userServiceContext } from '../../services/UserService.js';
import '../login/login.js';
import '../avatar/avatar.js';
import '../menu/menu.js';

registerIconLibrary('lucide', {
	resolver: name => `https://cdn.jsdelivr.net/npm/lucide-static/icons/${name}.svg`
});

export class TzStarter extends LitElement {
	
	static styles = styles;
	
	@provide({context: userServiceContext})
	private userService = new UserService();
	
	@state()
	initialized: boolean = false;
	
	/*
	mobileController = new MatchMediaController(this, IS_MOBILE);
	isMobile() {
		return this.mobileController.matches;
	}
	*/
	
	constructor() {
		super();
		this.init();
	}
	
	init() {
		this.userService.login().then(() => {
			this.initialized = true
		}).catch((error) => {
			console.error(error);
		});
	}
	
	render() {
		return html`
		${!this.initialized ?
	      	html``:
	      	html`${!this.userService.currentUser ?
		      	html`<tz-login></tz-login>`:
		      	html`
		      	<div class="application">
		
					<div class="top-bar">
						<tz-menu></tz-menu>
						<tz-avatar></tz-avatar>
					</div>
				
					<div class="app-content"></div>
				
				</div>`
	      	}`
      	}`;

	}
}