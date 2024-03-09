import { html, LitElement } from 'lit';
import { state, property } from 'lit/decorators.js';
import { consume } from '@lit/context';

import '@shoelace-style/shoelace/dist/components/button/button.js';
import '@shoelace-style/shoelace/dist/components/card/card.js';
import '@shoelace-style/shoelace/dist/components/rating/rating.js';
import '@shoelace-style/shoelace/dist/components/alert/alert.js';
import '@shoelace-style/shoelace/dist/components/icon/icon.js';

import styles from './list-actions-element.styles.js';

import { ActionService, actionServiceContext } from '../../services/ActionService.js';
import { Action } from '../../model/Action.js';

export class TzListActionsElement extends LitElement {
	
    static styles = styles;
    
    @consume({context: actionServiceContext})
    @state()
	private actionService?: ActionService;
	
	@property()
	action: Action | undefined;


	firstUpdated() {
	    // const button = this.renderRoot?.querySelector('sl-button.myButton') as SlButton;
    	/*
    	const alert = this.renderRoot?.querySelector('sl-alert.myAlert') as SlAlert;
    	const alertContent = alert.querySelector('.alert-content') as HTMLElement;
    	
    	alertContent.style.display = "flex";
    	alertContent.style.alignItems = "center";
    	alertContent.style.flexDirection = "row";
    	alertContent.style.gap = "10px";
    	*/
    	// button.addEventListener('click', () => alert.toast());
	}
	
	/*
	<sl-card>
  				${this.action?.name}
  				<div slot="footer">
			    	<sl-rating></sl-rating>
			    	<sl-button
			    		class="myButton"
			    		variant="primary"
			    	>Preview</sl-button>
			    </div>
			</sl-card>
			*/

/*
<sl-alert class="myAlert" variant="primary" open>
			    <sl-icon slot="icon" name="question-circle"></sl-icon>
			    <div class="alert-content">
				    <strong>${this.action?.name}</strong><br/>
				    <sl-rating></sl-rating>
				    <sl-button
			    		class="myButton"
			    		variant="primary"
			    	>Détail</sl-button>
			    </div>
			</sl-alert>
			<br/>
			
			<sl-card>
				<div class="card-content">
				    <strong>${this.action?.name}</strong>
				    <sl-icon-button name="gear" label="Settings"></sl-icon-button>
			    </div>
  				<div slot="footer">
				    <sl-button variant="primary" pill>Détail</sl-button>
				    <sl-rating></sl-rating>
			    </div>
			</sl-card>
			
			*/
    render() {
        return html`
        	
			<sl-card>
				<div class="card-content">
				    ${this.action?.name}
				    <sl-rating></sl-rating>
			    </div>
			</sl-card>
			
        `;
    }
}
