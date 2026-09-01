import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';

import { ElementGridViewComponent } from './components/element-grid-view/element-grid-view.component';


@Component({
    selector: 'app-root',
    standalone: true,
    imports: [  RouterOutlet,
                ElementGridViewComponent
            ],
    templateUrl: './app.component.html',
    styleUrl: './app.component.css'
})
export class AppComponent {
    title = 'Flox';
}
