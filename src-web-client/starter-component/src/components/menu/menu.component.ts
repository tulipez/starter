import { html, LitElement } from 'lit';
import { state } from 'lit/decorators.js';
import { consume } from '@lit/context';
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

import styles from './menu.styles.js';

import { UserService, userServiceContext } from '../../services/UserService.js';

export class TzMenu extends LitElement {
	
    static styles = styles;
    
    @consume({context: userServiceContext})
    @state()
	private userService?: UserService;
	
	@state()
	set darkMode(value: boolean) {
		const currentUser = this.userService?.currentUser;
		if(currentUser) currentUser.dark_mode = value;
	}
	
	get darkMode() : boolean {
		const currentUser = this.userService?.currentUser;
		return currentUser ? currentUser.dark_mode : false;
	}
	
	getDrawerMenu() : SlDrawer {
		return this.renderRoot?.querySelector('.drawer-menu') as SlDrawer;
	}
	
	getDrawerNewStarter() : SlDrawer {
		return this.renderRoot?.querySelector('.drawer-create-starter') as SlDrawer;
	}
	
	getDialogNewStarter() : SlDialog {
		return this.renderRoot?.querySelector('.dialog-create-starter') as SlDialog;
	}
	
	// TODO faire un darkModeController
	// ou refaire un darkMode qui marche correctement...
	applyDarkMode() {
		const htmlTag = document.querySelector('html') as HTMLElement;
		const bodyTag = document.querySelector('body') as HTMLElement;
		
		if (!this.darkMode) {
			htmlTag.classList.remove('sl-theme-dark');
			htmlTag.classList.add('sl-theme-light');
			bodyTag.classList.remove('sl-theme-dark');
			bodyTag.classList.add('sl-theme-light');
		}
		else {
			htmlTag.classList.add('sl-theme-dark');
			htmlTag.classList.remove('sl-theme-light');
			bodyTag.classList.add('sl-theme-dark');
			bodyTag.classList.remove('sl-theme-light');
		}
	}
	
	toggleDarkMode() {
		this.darkMode = !this.darkMode;
		this.applyDarkMode();
		this.userService?.saveCurrentUser();
	}
	
    render() {
		
		// TODO en attendant le darkModeController
		this.applyDarkMode();
		
        return html`
    	<sl-icon-button
			@click="${() => {this.getDrawerMenu().show();}}"
			class="menu-button"
			library="lucide"
			name="menu"
			label="Settings"
		></sl-icon-button>
				
		<sl-drawer
			class="drawer-menu"
			label="Starter"
			placement="start"
		>
		
			<sl-icon-button
				class="dark-mode-button"
				slot="header-actions"
				library="lucide"
				name="${this.darkMode ? "sun" : "moon"}"
				@click=${this.toggleDarkMode}
			></sl-icon-button>
			
			<sl-menu-item
				value="create_starter"
				@click="${() => {
					this.getDrawerMenu().hide();
					this.getDialogNewStarter().show();
				}}"
			>Créer un nouveau starter
				
				<sl-icon slot="prefix"
					library="lucide"
					name="plus"
					style="font-size:
					20px;"
				></sl-icon>
				
			</sl-menu-item>
			
			<sl-button
				slot="footer"
				variant="primary"
				@click="${() => {this.getDrawerMenu().hide();}}"
			>Fermer</sl-button>
			
		</sl-drawer>
				
		<sl-drawer
			class="drawer-create-starter"
			label="Nouveau starter"
			placement="bottom"
		>
		
			<sl-button
				slot="footer"
				variant="primary"
				@click="${() => {this.getDrawerNewStarter().hide();}}"
			>Créer</sl-button>
		
		</sl-drawer>
		
		<sl-dialog
			label="Nouveau starter"
			class="dialog-create-starter"
		>
		
			<div class="dialog-create-starter-form">formulaire</div>
			
			<sl-button
				slot="footer"
				@click="${() => {this.getDialogNewStarter().hide();}}"
			>Annuler</sl-button>
			
			<sl-button
				slot="footer"
				variant="primary"
				@click="${() => {this.getDialogNewStarter().hide();}}"
			>Enregistrer</sl-button>
		
		</sl-dialog>
		`;
    }
}
