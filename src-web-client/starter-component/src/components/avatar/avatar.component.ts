import { html, LitElement } from 'lit';
import '@shoelace-style/shoelace/dist/themes/light.css';
import '@shoelace-style/shoelace/dist/themes/dark.css';
import '@shoelace-style/shoelace/dist/components/avatar/avatar.js';
import '@shoelace-style/shoelace/dist/components/drawer/drawer.js';
import '@shoelace-style/shoelace/dist/components/dialog/dialog.js';
import '@shoelace-style/shoelace/dist/components/menu-item/menu-item.js';
import '@shoelace-style/shoelace/dist/components/button/button.js';
import '@shoelace-style/shoelace/dist/components/icon/icon.js';

import SlDrawer from '@shoelace-style/shoelace/dist/components/drawer/drawer.component.js';
import SlDialog from '@shoelace-style/shoelace/dist/components/dialog/dialog.component.js';

import styles from './avatar.styles.js';

import User from '../../model/User.js';


export class TzAvatar extends LitElement {
	
    static styles = styles;
    
    user = {} as User;
	
    getDrawerAvater() : SlDrawer {
		return this.renderRoot?.querySelector('.drawer-avatar') as SlDrawer;
	}
	
    getDialogConfirmLogout() : SlDialog {
		return this.renderRoot?.querySelector('.confirm-logout') as SlDialog;
	}
	
	async logout() {
		const response = await fetch('/api/logout', {
		method: 'GET',
		headers: {
			'Content-Type': 'application/json',
		},
		});
		if (!response.ok) {
			console.log(await response.text());
		}
		else {
			const resultLogout = await response.json();
			if(resultLogout && resultLogout.ok) {
				window.location.href = "/";
			}
			else {
				console.log(`error : ${JSON.stringify(resultLogout)}`);
			}
		}
	}
    
    render() {
		if(this.user==null) return html``;
		
        return html`
        <div>
    	<button class="avatar-button" type="button"
    		@click="${() => {this.getDrawerAvater().show();}}"
    	>
    	
			<sl-avatar
			  	image="${this.user.picture}"
			  	loading="lazy"
			></sl-avatar>
			
		</button>
		
		<sl-drawer class="drawer-avatar" label="Profil">
		
			<sl-menu-item
				value="logout"
				@click="${() => {this.getDialogConfirmLogout().show();}}"
			>Se déconnecter
			
				<sl-icon
					slot="prefix"
					library="lucide"
					name="log-out"
					style="font-size: 20px;"
				></sl-icon>
				
			</sl-menu-item>
			
			<sl-button
				slot="footer"
				variant="primary"
				@click="${() => {this.getDrawerAvater().hide();}}"
			>Fermer</sl-button>
		
		</sl-drawer>
		
		<sl-dialog class="confirm-logout" label="Déconnexion">
		
			Voulez-vous continuer?
			
			<sl-button
				slot="footer"
				@click="${() => {this.getDialogConfirmLogout().hide();}}"
			>Annuler</sl-button>
			
			<sl-button
				slot="footer"
				variant="primary"
				@click="${this.logout}"
			>Se déconnecter</sl-button>
			
		</sl-dialog>
		</div>
		`;
    }
}
