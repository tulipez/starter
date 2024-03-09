/* eslint-disable lit-a11y/no-autofocus */
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
import '@shoelace-style/shoelace/dist/components/input/input.js';

import SlDrawer from '@shoelace-style/shoelace/dist/components/drawer/drawer.component.js';
import SlDialog from '@shoelace-style/shoelace/dist/components/dialog/dialog.component.js';
import SlInput from '@shoelace-style/shoelace/dist/components/input/input.component.js';

import styles from './menu-general.styles.js';

import { WorkspaceService, workspaceServiceContext } from '../../services/WorkspaceService.js';
import { ActionService, actionServiceContext } from '../../services/ActionService.js';
import { Workspace } from '../../model/Workspace.js';

export class TzMenuGeneral extends LitElement {
	
    static styles = styles;
	
    @consume({context: workspaceServiceContext})
    @state()
	workspaceService?: WorkspaceService;
	
	@consume({context: actionServiceContext})
    @state()
	actionService?: ActionService;
	
	private getWorkspace() : Workspace | undefined{
		return this.workspaceService?.currentWorkspace;
	}
	
	@state()
	set darkMode(value: boolean) {
		const currentWorkspace = this.getWorkspace();
		if(currentWorkspace) {
			currentWorkspace.dark_mode = value;
		}
	}
	
	get darkMode() : boolean {
		const currentWorkspace = this.getWorkspace();
		return currentWorkspace ? currentWorkspace.dark_mode : false;
	}
	
	getDrawerMenu() : SlDrawer {
		return this.renderRoot?.querySelector('.drawer-menu') as SlDrawer;
	}
	
	getDrawerNewAction() : SlDrawer {
		return this.renderRoot?.querySelector('.drawer-create-starter') as SlDrawer;
	}
	
	getDialogNewAction() : SlDialog {
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
		this.workspaceService?.updateCurrentWorkspace();
	}
	
	createAction() {
		const actionNameInput = this.renderRoot?.querySelector('#action_name_input') as SlInput;
		this.actionService?.createAction({name: actionNameInput.value}).then(() => {
			this.getDialogNewAction().hide();
		}).catch((error) => {
			console.error(error);
		});
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
					this.getDialogNewAction().show();
				}}"
			>Créer une nouvelle action
				
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
				@click="${() => {this.getDrawerNewAction().hide();}}"
			>Créer</sl-button>
		
		</sl-drawer>
		
		<sl-dialog
			label="Nouvelle action"
			class="dialog-create-starter"
		>
		
			<div class="dialog-create-starter-form">
				<sl-input autofocus id="action_name_input"
					label="Nom"
				></sl-input>
			</div>
			
			<sl-button
				slot="footer"
				@click="${() => {this.getDialogNewAction().hide();}}"
			>Annuler</sl-button>
			
			<sl-button
				slot="footer"
				variant="primary"
				@click="${this.createAction}"
			>Enregistrer</sl-button>
		
		</sl-dialog>
		`;
    }
}
