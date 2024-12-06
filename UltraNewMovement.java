package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp

public class UltraNewMovement extends LinearOpMode{


    public void runOpMode(){
        DcMotor LB = hardwareMap.get(DcMotor.class, "leftBack");
        DcMotor LF = hardwareMap.get(DcMotor.class, "leftFront");
        DcMotor RB = hardwareMap.get(DcMotor.class, "rightBack");
        DcMotor RF = hardwareMap.get(DcMotor.class, "rightFront");
        DcMotor LVS = hardwareMap.get(DcMotor.class, "leftSlide");
        DcMotor RVS = hardwareMap.get(DcMotor.class, "rightSlide");
        Servo yslide = hardwareMap.get(Servo.class, "Yslide");
        Servo yslide2 = hardwareMap.get(Servo.class, "Yslide2");
        Servo yrotate = hardwareMap.get(Servo.class, "Yrotate");
        Servo yrotate2 = hardwareMap.get(Servo.class, "Yrotate2");
        Servo Rrotate = hardwareMap.get(Servo.class, "Rrotate");
        Servo gripper = hardwareMap.get(Servo.class, "Gripper");
        Servo gripper2 = hardwareMap.get(Servo.class, "Gripper2");
        Servo neck = hardwareMap.get(Servo.class, "Neck");
        Servo Lneck = hardwareMap.get(Servo.class, "leftNeck");
        Servo Rneck = hardwareMap.get(Servo.class, "rightNeck");


        double YslPos = 0.02;
        double YslPos2 = 0.98;
        double YrPos = 0.5;
        double YrPos2 = 0.5;
        double RrPos = 0.15;
        double SGSSpeed = 0;
        double slidespeed = 0;
        double Tslidespeed = 0;
        double VSpos = 0;
        boolean isWaiting = false;
        boolean isSleeping = false;
        boolean isOpen = true;
        boolean lastYState = false;
        boolean lastAState = false;
        boolean lastXState = false;
        RF.setDirection(DcMotor.Direction.REVERSE);
        RB.setDirection(DcMotor.Direction.REVERSE);
        LF.setDirection(DcMotor.Direction.FORWARD);
        LB.setDirection(DcMotor.Direction.FORWARD);
        
        LVS.setDirection(DcMotor.Direction.REVERSE);
        RVS.setDirection(DcMotor.Direction.REVERSE);
        LVS.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        RVS.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        LVS.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        LVS.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        RVS.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        RVS.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        waitForStart();
        yslide.setPosition(0.5);
        yrotate.setPosition(0.5);
        yrotate2.setPosition(0.5);
        gripper.setPosition(0.9);
        Lneck.setPosition(1);
        Rneck.setPosition(0);
        while (opModeIsActive()) {
            LB.setPower((gamepad1.left_stick_y - gamepad1.right_stick_x + Tslidespeed) * 0.5);
            RF.setPower((gamepad1.left_stick_y + gamepad1.right_stick_x + Tslidespeed) * 0.5);
            RB.setPower((gamepad1.left_stick_y + gamepad1.right_stick_x - Tslidespeed) * 0.5);
            LF.setPower((gamepad1.left_stick_y - gamepad1.right_stick_x - Tslidespeed) * 0.5);
            yslide.setPosition(YslPos);
            yslide2.setPosition(YslPos2);
            Rrotate.setPosition(RrPos);
            VSpos = LVS.getCurrentPosition();
            Tslidespeed = slidespeed + SGSSpeed;

            if ((gamepad2.left_stick_y < 0 && VSpos <= -2906) || (gamepad2.left_stick_y > 0 && VSpos >= -3)){
                LVS.setPower(0);
                RVS.setPower(0);
            }
            else {
                LVS.setPower(gamepad2.left_stick_y);
                RVS.setPower(gamepad2.left_stick_y);
            }

            if (YslPos > 0.44){
                YslPos = 0.44;
                YslPos2 = 0.56;
            }
            if(gamepad2.left_trigger > 0){//backward
                Lneck.setPosition(1);
                Rneck.setPosition(0);
            }
            else if (gamepad2.right_trigger > 0){//forward
                Lneck.setPosition(0);
                Rneck.setPosition(1);
            }
            if (gamepad2.dpad_left){
                SGSSpeed = -0.5;
            }
            else if (gamepad2.dpad_right){
                SGSSpeed = 0.5;
            }
            else {
                SGSSpeed = 0;
            }

            if (gamepad1.right_bumper){
                slidespeed = 1;
            }
            else if (gamepad1.left_bumper){
                slidespeed = -1;
            }
            else if (gamepad1.right_trigger == 1){
                slidespeed = 0.5;
            }
            else if (gamepad1.left_trigger == 1) {
                slidespeed = -0.5;
            }
            else{
                slidespeed = 0;
            }

            if (gamepad2.dpad_down && YslPos >=0 && YslPos2 <= 1){//go backward
                YslPos = YslPos - 0.01;
                YslPos2 = YslPos2 + 0.01;
                sleep(15);
            }
            else if (gamepad2.dpad_up && YslPos <= 0.44 && YslPos2 >= 0.56){;// go forward
                YslPos = YslPos + 0.01;
                YslPos2 = YslPos2 - 0.01;
                sleep(15);
            }
            else if (gamepad2.left_bumper){
                YslPos = 0.02;
                YslPos2 = 0.98;
            }
            if (gamepad2.y && !lastYState) {
                // Toggle waiting state and set servo positions
                isWaiting = !isWaiting;
                isSleeping = false; // Reset sleeping state when entering waiting/not-waiting
                yrotate.setPosition(isWaiting ? 0.25 : 0.15);
                yrotate2.setPosition(isWaiting ? 0.75 : 0.85);
            }

            if (gamepad2.a && !lastAState) {
                // Toggle sleeping state and set servo positions
                isSleeping = !isSleeping;
                isWaiting = false; // Reset waiting state when entering sleeping/not-sleeping
                yrotate.setPosition(isSleeping ? 0.9 : 0.5);
                yrotate2.setPosition(isSleeping ? 0.1 : 0.5);
            }

            if (gamepad2.x && !lastXState){
                isOpen = !isOpen;
                if (isOpen){
                    yrotate.setPosition(0.15);
                    yrotate2.setPosition(0.85);
                    sleep(200);
                    if(isWaiting) {
                        YslPos = YslPos + 0.03;
                        YslPos2 = YslPos2 - 0.03;
                        yslide.setPosition(YslPos);
                        yslide2.setPosition(YslPos2);
                        sleep(300);
                    }
                    gripper.setPosition(0.15);
                    sleep(200);
                    RrPos = 0.5;
                    sleep(200);
                    yrotate.setPosition(0.9);
                    yrotate2.setPosition(0.1);

                }
                else{
                    gripper.setPosition(0.9);// increase to open
                }


            }
            if (gamepad2.right_stick_x > 0 && RrPos <= 1){
                RrPos  = RrPos + 0.01;
                sleep(15);
            }
            else if (gamepad2.right_stick_x < 0 && RrPos >= 0){
                RrPos = RrPos - 0.01;
                sleep(15);
            }
            lastXState = gamepad2.x;
            lastYState = gamepad2.y;
            lastAState = gamepad2.a;
            telemetry.addData("gamepad2leftsticky", gamepad2.left_stick_y);
            telemetry.addData("LVS Position", VSpos);
            telemetry.addData("YslPos",YslPos);
            telemetry.addData("YslPos2",YslPos2);
            telemetry.addData("RrPos",RrPos);
            telemetry.addData("isWaiting",isWaiting);
            telemetry.addData("iSleeping",isSleeping);
            telemetry.addData("isOpen",isOpen);
            telemetry.addData(("gamepad1"),gamepad1.left_stick_y);
            telemetry.update();

        }
    }
}
