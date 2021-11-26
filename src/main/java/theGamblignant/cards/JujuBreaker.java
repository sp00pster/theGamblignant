package theGamblignant.cards;

import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theGamblignant.VriskaMod;
import theGamblignant.characters.TheGamblignant;

import static theGamblignant.VriskaMod.makeCardPath;

public class JujuBreaker extends AbstractVriskaCard {

    public static final String ID = VriskaMod.makeID(JujuBreaker.class.getSimpleName());
    public static final String IMG = makeCardPath("Attack.png");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheGamblignant.Enums.COLOR_COBALT;

    private static final int COST = 3;

    private static final int DAMAGE = 7;


    public JujuBreaker() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseDamage = DAMAGE;
        magicNumber = baseMagicNumber;
        this.exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
        if (!this.upgraded) {
            for (AbstractPower i : p.powers) {
                if (i.type == AbstractPower.PowerType.BUFF) {
                    this.addToBot(new RemoveSpecificPowerAction(p, p, i));
                }
            }
        }
        for (AbstractPower i : m.powers) {
            if (i.type == AbstractPower.PowerType.BUFF) {
                this.addToBot(new RemoveSpecificPowerAction(m, m, i));
            }
        }
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            this.rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}
