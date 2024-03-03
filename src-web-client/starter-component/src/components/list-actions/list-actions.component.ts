import { html, LitElement } from 'lit';
import { state } from 'lit/decorators.js';
import { consume } from '@lit/context';

import '@shoelace-style/shoelace/dist/themes/light.css';
import '@shoelace-style/shoelace/dist/themes/dark.css';
import '@shoelace-style/shoelace/dist/components/button/button.js';
import '@shoelace-style/shoelace/dist/components/icon/icon.js';
import '@shoelace-style/shoelace/dist/components/input/input.js';
import '@shoelace-style/shoelace/dist/components/rating/rating.js';
import '@shoelace-style/shoelace/dist/components/alert/alert.js';
import styles from './list-actions.styles.js';

import { IObserver } from '../../controllers/IObserver.js';
import { WorkspaceService, workspaceServiceContext } from '../../services/WorkspaceService.js';
import { ActionService, actionServiceContext, EVENT_ACTION_CREATED } from '../../services/ActionService.js';
import { Action } from '../../model/Action.js';

export class TzListActions extends LitElement {
	
    static styles = styles;
    
    @consume({context: workspaceServiceContext})
    @state()
	private workspaceService?: WorkspaceService;
	
    @consume({context: actionServiceContext})
    @state()
	private actionService?: ActionService;

	private actionServiceObserver = <IObserver<Action>>{
	    notify: (message: string, action: Action) => {
			if(message === EVENT_ACTION_CREATED) {
				console.log("TzListActions : ", JSON.stringify(action));
				this.requestUpdate();
			}
	    }
	};
	
	connectedCallback() {
		super.connectedCallback();
		this.actionService?.subscribe(this.actionServiceObserver);
	}
	
	disconnectedCallback() {
		super.disconnectedCallback();
		this.actionService?.unsubscribe(this.actionServiceObserver);
	}
	
    render() {
        return html`
        TzListActions
        <ul>
		    ${this.workspaceService?.currentWorkspace?.actions.map((action) =>
		    	html`<li>${action.name}</li>`
		    )}
	    </ul>
        `;
    }
}
