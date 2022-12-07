import { Component, OnInit } from '@angular/core';
import { ModalAddMeasuUnitComponent } from 'src/app/components/MeasurementUnitComponents/modal-add-measu-unit/modal-add-measu-unit.component';
import IMeasurementUnit from 'src/app/interfaces/IMeasurementUnit';
import { AuthService } from 'src/app/service/auth.service';
import { MeasurementUnitService } from 'src/app/service/measurement-unit.service';
import { buttonPermission } from 'src/app/utils/utils';

@Component({
  selector: 'app-measurement-unit',
  templateUrl: './measurement-unit.component.html',
  styleUrls: ['./measurement-unit.component.css'],
})
export class MeasurementUnitComponent implements OnInit {
  component = ModalAddMeasuUnitComponent;
  measurementUnits: IMeasurementUnit[];
  searchText: string;

  constructor(
    private measurementUnitService: MeasurementUnitService,
    public auth: AuthService
  ) {}

  btnPermission = buttonPermission;

  ngOnInit(): void {
    this.measurementUnitService.measurementUnitChanged.subscribe(() =>
      this.getData()
    );
    this.getData();
  }

  getData() {
    this.measurementUnitService.get().subscribe((data) => {
      this.measurementUnits = data;
      console.log(this.measurementUnits);
    });
  }

  onSearchTextEntered(searchValue: string) {
    this.searchText = searchValue;
    this.measurementUnitService
      .getByLikeName(this.searchText)
      .subscribe((data) => {
        this.measurementUnits = data;
        console.log(this.measurementUnits);
      });
  }
}
