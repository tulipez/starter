import { html, LitElement } from 'lit';
import { property } from 'lit/decorators.js';
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

import { IS_MOBILE, MatchMediaController } from '../../controllers/MatchMedia.js';
import '../login/login.js';
import '../avatar/avatar.js';
import '../menu/menu.js';
import User from '../../model/User.js';

registerIconLibrary('lucide', {
	resolver: name => `https://cdn.jsdelivr.net/npm/lucide-static/icons/${name}.svg`
});

export class TzStarter extends LitElement {
	
	static styles = styles;
	
	mobileController = new MatchMediaController(this, IS_MOBILE);
	
	user = {} as User;
	
	isMobile() {
		return this.mobileController.matches;
	}
	
	@property({ type: Boolean }) debug = false;
	
	@property({ type: Boolean }) userLoaded = false;
	
	async loadUserDebug() {
		
		this.user = new User();
		this.user.name = "zulut";
		this.user.given_name = "zulut";
		this.user.family_name = "zulut";
		this.user.picture = "https://picsum.photos/18/18";
		this.user.email = "zulut@zulut.fr";
		this.user.email_verified = true;
		this.user.locale = "fr";
		
		this.userLoaded = true;
	}
	
	async loadUser() {
		const response = await fetch('/api/user', {
		method: 'GET',
		headers: {
			'Content-Type': 'application/json',
		},
		});
		if (!response.ok) {
			console.log(await response.text());
		}
		else {
			this.user = await response.json();
		}
		this.userLoaded = true;
	}
	
	connectedCallback() {
		super.connectedCallback();
		
		if(!this.userLoaded) {
			if(this.debug) {
				this.loadUserDebug();
			}
			else {
				this.loadUser();
			}
		}
	}
	
	// <tz-menu></tz-menu>
	
	render() {
		return html`
		${!this.userLoaded ? '' : (!this.user ? html`<tz-login></tz-login>` : html`
		
			<div class="application">
			
				<div class="top-bar">
					<tz-menu></tz-menu>
					<tz-avatar .user=${this.user}></tz-avatar>
				</div>
				
				<div class="app-content">
					
				</div>
				
			</div>
			
		`)}
		`;
	}
}