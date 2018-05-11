

#include <project.h>
#include <stdio.h>
#include "Motor.h"
#include "Ultra.h"
#include "Nunchuk.h"
#include "Reflectance.h"
#include "I2C_made.h"
#include "Gyro.h"
#include "Accel_magnet.h"
#include "IR.h"
#include "Ambient.h"
#include "Beep.h"
int rread(void);

/*//reflectance//
int main()
{
    struct sensors_ ref;
    struct sensors_ dig;
    CyGlobalIntEnable; 
    UART_1_Start();
    
    motor_start();
    sensor_isr_StartEx(sensor_isr_handler);
    
    reflectance_start();

    IR_led_Write(1);
    reflectance_digital(&dig);
    
    
    while( dig.l3 == 1 && dig.r3 == 1 )             // while black line is not senced
    {
        reflectance_digital(&dig);
        
        printf("%d %d  \r\n", dig.l3, dig.r3);    
    
        motor_forward(125,1);                           //keep moving forward
        printf("Detecting Line \n");
    }
                                                    //Stop the motor
        printf("Line Detected \n");
        PWM_Stop();
    
    while( ! get_IR() )                             //not getting some thing form IR
    {
                                                    //printf("Nothing form IR \n");
    }
        motor_start();
        printf("For Loop Started \n");
        motor_forward (255,1000);
    
    for (;;)
    {
        reflectance_read(&ref);
        printf("%d %d %d %d \r\n", ref.l3, ref.l1, ref.r1, ref.r3);       //print out each period of reflectance sensors
        reflectance_digital(&dig);                                        //print out 0 or 1 according to results of reflectance period
        printf("%d %d %d %d \r\n", dig.l3, dig.l1, dig.r1, dig.r3);       //print out 0 or 1 according to results of reflectance period
    
 
        
        if (ref.l3 > 20000)
        {
            motor_backward(255,150);
            prmotor_turn(255,255,150);
        }
        if (ref.r3 > 20000)
        {
            motor_backward(255,150);
            plmotor_turn(255,255,150);
        }
        if (ref.l3 > 20000 && ref.l1 > 20000 && ref.r1 > 20000 && ref.r3 > 20000)
        {
        motor_backward(255,150);
        prmotor_turn(255,255,150);
        }
        else    
        motor_forward(255,1);
        
        CyDelayUs(1);
         
    
    }
}
//*/
//2nd reflectance code/
int main()
{
    int i = 0, true = 0, fake = 0;
    CyGlobalIntEnable; 
    UART_1_Start();
    struct sensors_ ref;
    struct sensors_ dig;
    sensor_isr_StartEx(sensor_isr_handler);
    reflectance_start();
    motor_start();
    IR_led_Write(1);
    reflectance_digital(&dig);
    PWM_Start();
    
    while( dig.l3 == 1 && dig.r3 == 1 )         //while black line is not senced
    {
        reflectance_digital(&dig);
        printf("%d %d  \r\n", dig.l3, dig.r3);    
        motor_forward(125,1);                   //Keep moving forward
        printf("Detecting Line \n");
    }
    printf("Line Detected \n");             //stop moving
    PWM_Stop();
    
    while( ! get_IR() )                         //not getting some thing form IR
    {
        
    }
    motor_start();
    printf("For Loop Started \n");

    do
    { 
        reflectance_read(&ref);
        printf("%d %d %d %d \r\n", ref.l3, ref.l1, ref.r1, ref.r3);       //print out each period of reflectance sensors
        reflectance_digital(&dig);      //print out 0 or 1 according to results of reflectance period
        printf("%d %d %d %d \r\n", dig.l3, dig.l1, dig.r1, dig.r3); 
        
        if ( dig.l3 == 1 && dig.l1 == 0 && dig.r1 == 0 && dig.r3 == 1 ) // black line detected. Keep going
        {
            motor_forward(255,1);
            true = 0;
            fake = 0;
        }
        else
        if (dig.l3 == 1 && dig.l1 == 1 && dig.r1 == 0 && dig.r3 == 1) // turn right slowly
        {
            motor_turn(255,15,1);
            true = 0;
            fake = 0;
        }
        else
        if (dig.l3 == 1 && dig.l1 == 1 && dig.r1 == 0 && dig.r3 == 0) // turn right quickly
        {
            motor_turn(255,1,1);
            true = 0;
            fake = 0;
        }
        else
        if (dig.l3 == 1 && dig.l1 == 1 && dig.r1 == 1 && dig.r3 == 0) // sharp right turn
        {
            prmotor_turn(255,30,1);
            true = 0;
            fake = 0;
        }
        else
        if ( dig.l3 == 1 && dig.l1 == 0 && dig.r1 == 1 && dig.r3 == 1 ) // slowly turn left
        {
            motor_turn(15,255,1);
            true = 0;
            fake = 0;
        }
        else
        if (dig.l3 == 0 && dig.l1 == 1 && dig.r1 == 1 && dig.r3 == 1) // sharp turn left
        {
            plmotor_turn(30,255,1);
            true = 0;
            fake = 0;
        }
        else
        if (dig.l3 == 0 && dig.l1 == 0 && dig.r1 == 1 && dig.r3 == 1) // quickly turn left
        {
            motor_turn(1,255,1);
            true = 0;
            fake = 0;
        }           
        if (dig.l3 == 0 && dig.l1 == 0 && dig.r1 == 0 && dig.r3 == 0)
        { 
            true = 1;
            if ( true != fake)
            { 
                i++;
                fake = true;
            }
        }    
               CyDelayUs(1);
    }   while (i < 3);   
        motor_stop();                
                
        
    for(;;);    
}
      
        
        
        
        
        
        
        




#if 0
int rread(void)
{
    SC0_SetDriveMode(PIN_DM_STRONG);
    SC0_Write(1);
    CyDelayUs(10);
    SC0_SetDriveMode(PIN_DM_DIG_HIZ);
    Timer_1_Start();
    uint16_t start = Timer_1_ReadCounter();
    uint16_t end = 0;
    while(!(Timer_1_ReadStatusRegister() & Timer_1_STATUS_TC)) {
        if(SC0_Read() == 0 && end == 0) {
            end = Timer_1_ReadCounter();
        }
    }
    Timer_1_Stop();
    
    return (start - end);
}
#endif

/* Don't remove the functions below */
int _write(int file, char *ptr, int len)
{
    (void)file; /* Parameter is not used, suppress unused argument warning */
	int n;
	for(n = 0; n < len; n++) {
        if(*ptr == '\n') UART_1_PutChar('\r');
		UART_1_PutChar(*ptr++);
	}
	return len;
}

int _read (int file, char *ptr, int count)
{
    int chs = 0;
    char ch;
 
    (void)file; /* Parameter is not used, suppress unused argument warning */
    while(count > 0) {
        ch = UART_1_GetChar();
        if(ch != 0) {
            UART_1_PutChar(ch);
            chs++;
            if(ch == '\r') {
                ch = '\n';
                UART_1_PutChar(ch);
            }
            *ptr++ = ch;
            count--;
            if(ch == '\n') break;
        }
    }
    return chs;
}
/* [] END OF FILE */

               