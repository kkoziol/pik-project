export class EmailValidator{

  static mailValidate(control: string): boolean{

    const EMAIL_REGEXP = /^[a-z0-9!#$%&'*+\/=?^_`{|}~.-]+@[a-z0-9]([a-z0-9-]*[a-z0-9])?(\.[a-z0-9]([a-z0-9-]*[a-z0-9])?)*$/i;

    if (control != "" && (control.length <= 5 || !EMAIL_REGEXP.test(control))) {
      return false;
    }
    return true;
  }

}

