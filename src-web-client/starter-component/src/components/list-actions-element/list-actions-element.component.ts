import { html, LitElement } from 'lit';
import { state, property } from 'lit/decorators.js';
import { consume } from '@lit/context';

import '@shoelace-style/shoelace/dist/components/button/button.js';
import '@shoelace-style/shoelace/dist/components/card/card.js';
import '@shoelace-style/shoelace/dist/components/rating/rating.js';
import '@shoelace-style/shoelace/dist/components/alert/alert.js';
import '@shoelace-style/shoelace/dist/components/icon/icon.js';

import SlRating from '@shoelace-style/shoelace/dist/components/rating/rating.component.js';

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

	private getRating() : SlRating {
		return this.renderRoot?.querySelector('sl-rating') as SlRating;
	}
	
	onRatingChange() {
		if(this.action) {
			this.action.ratingValue = this.getRating().value;
			this.actionService?.updateAction(this.action);
		}
	}

    render() {
        return html`
			<sl-card>
				<div class="card-content">
				    ${this.action?.name}
				    ${this.action?.date}
				    <sl-rating
				    	value="${this.action?.ratingValue}"
				    	@sl-change="${this.onRatingChange}"
				    ></sl-rating>
			    </div>
			</sl-card>
        `;
    }
}
