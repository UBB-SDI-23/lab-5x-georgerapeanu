import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-abstract-overview-page',
  templateUrl: './abstract-overview-page.component.html',
  styleUrls: ['./abstract-overview-page.component.css']
})
export class AbstractOverviewPageComponent<TData> {
  @Input() data: TData[] = [];
  @Input() currentPage: number = 0;
  @Input() currentSize: number = 10;
  @Input() actionable: boolean = false;
}
