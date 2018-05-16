import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SmallAccountProfileComponent } from './small-account-profile.component';

describe('SmallAccountProfileComponent', () => {
  let component: SmallAccountProfileComponent;
  let fixture: ComponentFixture<SmallAccountProfileComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SmallAccountProfileComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SmallAccountProfileComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
